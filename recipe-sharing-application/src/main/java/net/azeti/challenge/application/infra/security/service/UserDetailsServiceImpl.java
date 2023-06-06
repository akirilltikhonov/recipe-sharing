package net.azeti.challenge.application.infra.security.service;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.UserRepository;
import net.azeti.challenge.application.infra.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with such username doesn't exist"));
        return SecurityUser.fromUser(user);
    }
}
