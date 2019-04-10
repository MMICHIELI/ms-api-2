package com.msapi2.authservice.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.GrantedAuthority;

import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.ldap.UserDetailsContextMapper;

import javax.naming.directory.Attributes;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomLdapUserDetailMapper implements UserDetailsContextMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomLdapUserDetailMapper.class);

  @Override
  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, GrantedAuthority[] grantedAuthorities) {
    Attributes attributes = ctx.getAttributes();
    Object[] groups = new Object[100];
    groups = ctx.getObjectAttributes("memberOf");
    LOGGER.debug("Attributes: {}", attributes);

    Set<GrantedAuthority> authority = new HashSet<>();

    LOGGER.info("Loading all groups for the user: " + username);
    for (Object o: groups) {

      LOGGER.info(o.toString());

      authority.add(new SimpleGrantedAuthority(o.toString()));
    }

    authority.add(new SimpleGrantedAuthority("ROLE_USER"));
    authority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

    LOGGER.info("Done.");

    return new User(username, "", false, false, false, authority);
  }

  @Override
  public void mapUserToContext(UserDetails userDetails, DirContextAdapter dirContextAdapter) {

  }
}
