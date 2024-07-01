package com.miniconomy.commercial_bank_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CertificateFilterConfig
{
    @Value("${certificate.filter.enabled}")
    private boolean filterEnabled;

    @Bean
    public FilterRegistrationBean<CertificateFilter> certificateFilterRegistration()
    {
        FilterRegistrationBean<CertificateFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CertificateFilter());
        registrationBean.addUrlPatterns("/loans");
        registrationBean.addUrlPatterns("/loans/*");

        registrationBean.addUrlPatterns("/debitOrders");
        registrationBean.addUrlPatterns("/debitOrders/*");

        registrationBean.addUrlPatterns("/account/balance");

        registrationBean.addUrlPatterns("/transactions");
        registrationBean.addUrlPatterns("/transactions/*");

        registrationBean.setOrder(1);

        registrationBean.setEnabled(filterEnabled);

        return registrationBean;
    }
}
