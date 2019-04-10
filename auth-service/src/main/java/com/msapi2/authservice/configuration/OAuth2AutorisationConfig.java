package com.msapi2.authservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.security.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * OAuth2 Configuration Server Class.
 *
 * @link @EnableAuthorizationServer enables an authorization server and a TokenEndPoint in the curren application context.
 * @link AuthorizationServerConfigurerAdapter allows to customize the authorisation configuration.
 *
 * The Token of authentication is crypted and decrypted using privateKey and publicKey.
 *
 * Only one client is allowed to use the Oauth2 Server:
 * msApi2Client/msApi2Secret
 *
 * @author michi
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AutorisationConfig extends AuthorizationServerConfigurerAdapter {

    private TokenStore tokenStore = new InMemoryTokenStore();

    @Value("${oauth2.privateKey}")
    private String privateKey;

    @Value("${oauth2.publicKey}")
    private String publicKey;

    @Value("${oauth2.accessTokenValiditySeconds}")
    private int accessTokenValiditySeconds;

    @Value("${oauth2.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(this.privateKey);
        converter.setVerifierKey(this.publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }

    @Bean
    @Primary
    public ConsumerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    /**
     * Allow Grant Type: "authorization_code", "refresh_token", "password", "client_credentials"
     * Defines the Client credentials needed to access Api.
     * We can put this data on database to allow the usage of server by multiple applicaqtion/client
     * Now Only 'msApi2Client' client can be served
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("msApi2Client").secret("msApi2Secret")
            .authorizedGrantTypes("authorization_code", "refresh_token", "password", "client_credentials")
            .scopes("server")
            .accessTokenValiditySeconds(this.accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(this.refreshTokenValiditySeconds);
    }

    /**
     * Defines the authorization and token endpoints and the token services
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(this.tokenStore).accessTokenConverter(tokenConverter())
            .authenticationManager(this.authenticationManager);
    }

    /**
     * Security Contraints
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        oauthServer.allowFormAuthenticationForClients();
    }

    @Configuration
    public static class AuthenticationManagerProvider extends WebSecurityConfigurerAdapter {

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}
