package com.msapi2.authservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;



@SuppressWarnings("deprecation")
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LdapConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Value("${ldap.urls}")
    private String ldapUrls;

    @Value("${ldap.username}")
    private String ldapSecurityPrincipal;

    @Value("${ldap.password}")
    private String ldapPrincipalPassword;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Value("${ldap.enabled}")
    private String ldapEnabled;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void init(final AuthenticationManagerBuilder authenticationManager) throws Exception {
        if (Boolean.parseBoolean(this.ldapEnabled)) {
            authenticationManager.ldapAuthentication().contextSource().url(this.ldapUrls).managerDn(this.ldapPrincipalPassword)
                .managerPassword(this.ldapPrincipalPassword)
                .and().userDnPatterns(this.ldapUserDnPattern).userDetailsContextMapper(new CustomLdapUserDetailMapper());
        } else {
            authenticationManager.inMemoryAuthentication().withUser(User.withUsername("user").password("password").roles("USER").build())
                .withUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
        }
    }
}
