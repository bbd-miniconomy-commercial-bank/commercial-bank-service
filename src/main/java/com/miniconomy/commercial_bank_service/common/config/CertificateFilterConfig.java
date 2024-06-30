package com.miniconomy.commercial_bank_service.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miniconomy.commercial_bank_service.auth_validation_filter.CertificateFilter;

@Configuration
public class CertificateFilterConfig
{
    @Autowired
    private CertificateFilter certificateFilter;

    @Bean
    public FilterRegistrationBean<CertificateFilter> certificateFilterRegistration()
    {
        FilterRegistrationBean<CertificateFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(certificateFilter);
        registrationBean.addUrlPatterns("/loans");
        registrationBean.addUrlPatterns("/loans/*");

        registrationBean.addUrlPatterns("/debitOrders");
        registrationBean.addUrlPatterns("/debitOrders/*");

        registrationBean.addUrlPatterns("/account/balance");

        registrationBean.addUrlPatterns("/transactions");
        registrationBean.addUrlPatterns("/transactions/*");

        return registrationBean;
    }
}
