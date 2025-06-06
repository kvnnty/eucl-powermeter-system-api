package com.kvn.eucl.v1.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kvn.eucl.v1.security.user.UserPrincipal;

@Component("auditorAware")
public class AuditAwareConfig implements AuditorAware<UUID> {
  @Override
  public Optional<UUID> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
      return Optional.empty();
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof UserPrincipal userPrincipal) {
      return Optional.ofNullable(userPrincipal.getId());
    } else {
      return Optional.empty();
    }
  }

}
