package com.miniconomy.commercial_bank_service.security;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Optional;

@Component
public class CertificateFilter implements Filter
{
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.certstore.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.certstore.bucket.filename}")
    private String trustFileName;

    private final AccountService accountService;

    public CertificateFilter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

    @Override
	public void destroy() {
	}

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws ServletException, IOException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String clientCertHeader = req.getHeader("x-amzn-mtls-clientcert");

        if (clientCertHeader == null)
        {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client certificate required");
            return;
        }

        Optional<Account> accountOptional = accountService.retrieveAccountByCn(clientCertHeader);
        if(accountOptional.isEmpty())
        {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account not found for Common Name: " + clientCertHeader);
            return;
        }

        Account account = accountOptional.get();
        String accountName = account.getAccountName();
        
        request.setAttribute("accountName", accountName);

        // try
        // {
        //     X509Certificate clientCert = loadCertificateFromHeader(clientCertHeader);
        //     X509Certificate trustedCert = loadTrustedCertificateFromS3();

        //     boolean isVerified = verifyCertificate(clientCert, trustedCert);
        //     if (!isVerified)
        //     {
        //         res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client certificate verification failed");
        //         return;
        //     }

        //     String cn = extractCommonName(clientCert);
        //     Optional<Account> accountOptional = accountService.retrieveAccountByCn(cn);
        //     if(accountOptional.isEmpty())
        //     {
        //         res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account not found for Common Name: " + cn);
        //         return;
        //     }

        //     Account account = accountOptional.get();
        //     String accountName = account.getAccountName();
            
        //     request.setAttribute("accountName", accountName);

        //     filterChain.doFilter(request, response);
        // }
        // catch (Exception e)
        // {
        //     res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing client certificate");
        // }
    }

    private X509Certificate loadCertificateFromHeader(String clientCertHeader) throws Exception
    {        
        String cleanedCertHeader = clientCertHeader.replace("-----BEGIN%20CERTIFICATE-----", "")
                                                    .replace("-----END%20CERTIFICATE-----", "")
                                                    .replace("%0A", "")
                                                    .replace("%20", "");
                                                    
        byte[] certBytes = Base64.getDecoder().decode(cleanedCertHeader);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }

    private X509Certificate loadTrustedCertificateFromS3() throws Exception
    {
        S3Object s3Object = amazonS3.getObject(bucketName, trustFileName);
        String certContent = new String(s3Object.getObjectContent().readAllBytes());

        try (PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(certContent.getBytes()))))
        {
            X509CertificateHolder certificateHolder = (X509CertificateHolder) pemParser.readObject();
            return new JcaX509CertificateConverter().getCertificate(certificateHolder);
        }

    }

    private boolean verifyCertificate(X509Certificate clientCert, X509Certificate trustedCert) throws Exception
    {
        try
        {
            clientCert.verify(trustedCert.getPublicKey());
            clientCert.checkValidity();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private String extractCommonName(X509Certificate certificate) throws Exception
    {
        X500Name x500Name = new X500Name(certificate.getSubjectX500Principal().getName());
        RDN cnRdn = x500Name.getRDNs(BCStyle.CN)[0];
        return IETFUtils.valueToString(cnRdn.getFirst().getValue());
    }
}

