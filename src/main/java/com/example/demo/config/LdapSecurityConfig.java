package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

@Order(4)
@Configuration
@EnableWebSecurity
public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.base.dn}")
    private String ldapBaseDn;

    @Value("${ldap.username}")
    private String ldapSecurityPrincipal;

    @Value("${ldap.password}")
    private String ldapPrincipalPassword;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(ldapAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider ldapAuthenticationProvider() {
        final DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapUrl);
        contextSource.setUserDn(ldapSecurityPrincipal);
        contextSource.setPassword(ldapPrincipalPassword);
        contextSource.afterPropertiesSet();
        final LdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch(ldapBaseDn, ldapUserDnPattern, contextSource);
        final BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
        bindAuthenticator.setUserSearch(ldapUserSearch);
        return new LdapAuthenticationProvider(bindAuthenticator, new DefaultLdapAuthoritiesPopulator(contextSource, ldapBaseDn));
    }

}
