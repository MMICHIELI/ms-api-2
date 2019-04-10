package com.msapi2.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * OAuth 2 Resource Server class
 *
 * @link @EnableResourceServer enable Spring Security filter to authenticates the request
 *
 * Default configuration is used. We can override configuration by extendint @link ResourceServerConfigurerAdapter
 *
 * @author MMichieli
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers("/webjars/**", "/oauth/token")
            .permitAll();
    }
}
