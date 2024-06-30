package com.miniconomy.commercial_bank_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CertificateFilterConfig
{
    @Autowired
    private CertificateFilter certificateFilter;

    @Value("${certificate.filter.enabled:false}")
    private boolean filterEnabled;

    @Bean
    public FilterRegistrationBean<CertificateFilter> certificateFilterRegistration()
    {
        FilterRegistrationBean<CertificateFilter> registrationBean = new FilterRegistrationBean<>();

        if(filterEnabled)
        {
          registrationBean.setFilter(certificateFilter);
          registrationBean.addUrlPatterns("/loans");
          registrationBean.addUrlPatterns("/loans/*");
  
          registrationBean.addUrlPatterns("/debitOrders");
          registrationBean.addUrlPatterns("/debitOrders/*");
  
          registrationBean.addUrlPatterns("/account/balance");
  
          registrationBean.addUrlPatterns("/transactions");
          registrationBean.addUrlPatterns("/transactions/*");
        }

        return registrationBean;
    }
}
