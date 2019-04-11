package com.msapi2.authservice.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.GrantedAuthority;
//
//import org.springframework.security.GrantedAuthorityImpl;
//import org.springframework.security.userdetails.User;
//import org.springframework.security.userdetails.UserDetails;
//import org.springframework.security.userdetails.ldap.UserDetailsContextMapper;

import javax.naming.directory.Attributes;
import java.util.HashSet;
import java.util.Set;
//import java.util.Set;


public class CustomLdapUserDetailMapper implements ContextMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomLdapUserDetailMapper.class);


  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, GrantedAuthority[] grantedAuthorities) {
    Attributes attributes = ctx.getAttributes();
    Object[] groups = new Object[100];
    groups = ctx.getObjectAttributes("memberOf");
    LOGGER.debug("Attributes: {}", attributes);

    Set<GrantedAuthority> authority = new HashSet<>();

    LOGGER.info("Loading all groups for the user: " + username);
    for (Object o: groups) {

      LOGGER.info(o.toString());

      authority.add(new GrantedAuthorityImpl(o.toString()));
    }

    authority.add(new GrantedAuthorityImpl("ROLE_USER"));
    authority.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

    LOGGER.info("Done.");

    return new User(username, "", true, false, false, false, authority);
  }

}
