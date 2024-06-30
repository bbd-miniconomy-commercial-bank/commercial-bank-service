package com.miniconomy.commercial_bank_service.auth_validation_filter;

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
import org.springframework.web.filter.OncePerRequestFilter;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class CertificateFilter extends OncePerRequestFilter
{
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String clientCertHeader = request.getHeader("X-Amzn-Mtls-Clientcert");

        if (clientCertHeader == null)
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client certificate required");
            return;
        }

        try
        {
            X509Certificate clientCert = loadCertificateFromHeader(clientCertHeader);
            List<X509Certificate> trustedCerts = loadTrustedCertificatesFromS3();

            boolean isVerified = verifyCertificate(clientCert, trustedCerts);
            if (!isVerified)
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client certificate verification failed");
                return;
            }

            String cn = extractCommonName(clientCert);
            request.setAttribute("clientCN", cn);

            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing client certificate");
        }
    }

    private X509Certificate loadCertificateFromHeader(String clientCertHeader) throws Exception
    {
        byte[] certBytes = Base64.getDecoder().decode(clientCertHeader);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }

    private List<X509Certificate> loadTrustedCertificatesFromS3() throws Exception
    {
        List<S3ObjectSummary> objectSummaries = amazonS3.listObjects(bucketName).getObjectSummaries();
        List<X509Certificate> trustedCerts = new ArrayList<>();

        for (S3ObjectSummary summary : objectSummaries)
        {
            S3Object s3Object = amazonS3.getObject(bucketName, summary.getKey());
            try (PEMParser pemParser = new PEMParser(new InputStreamReader(s3Object.getObjectContent())))
            {
                X509CertificateHolder certificateHolder = (X509CertificateHolder) pemParser.readObject();
                trustedCerts.add(new JcaX509CertificateConverter().getCertificate(certificateHolder));
            }
        }

        return trustedCerts;
    }

    private boolean verifyCertificate(X509Certificate clientCert, List<X509Certificate> trustedCerts) throws Exception
    {
        for (X509Certificate trustedCert : trustedCerts)
        {
            try
            {
                clientCert.verify(trustedCert.getPublicKey());
                return true;
            }
            catch (Exception e)
            {
                // Ignore and try the next trusted certificate
            }
        }
        return false;
    }

    private String extractCommonName(X509Certificate certificate) throws Exception
    {
        X500Name x500Name = new X500Name(certificate.getSubjectX500Principal().getName());
        RDN cnRdn = x500Name.getRDNs(BCStyle.CN)[0];
        return IETFUtils.valueToString(cnRdn.getFirst().getValue());
    }
}

