package com.forohub.forohub.config;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Find user in the database
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    // Build UserDetails object
    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(stripRolePrefix(user.getRole()))
        .build();

    System.out.println("User: " + user.getUsername() + ", Authorities: " + userDetails.getAuthorities());

    return userDetails;
  }

  /**
   * Remove ROLE_ prefix if present because Spring will add it automatically
   */
  private String stripRolePrefix(String role) {
    if (role == null) return "";
    return role.startsWith("ROLE_") ? role.substring(5) : role;
  }
}