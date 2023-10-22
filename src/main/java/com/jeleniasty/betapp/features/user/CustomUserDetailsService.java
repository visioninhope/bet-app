package com.jeleniasty.betapp.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final BetappUserRepository betappUserRepository;

  @Override
  public UserPrincipal loadUserByUsername(String username)
    throws UsernameNotFoundException {
    return UserPrincipal.create(
      betappUserRepository
        .findByUsernameOrEmail(username, username)
        .orElseThrow(() ->
          new UsernameNotFoundException("User " + username + " not found.")
        )
    );
  }
}
