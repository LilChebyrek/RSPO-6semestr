package ru.sibsutis.shop.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.api.dto.RegisterRequestDto;
import ru.sibsutis.shop.core.model.entity.user.Customer;
import ru.sibsutis.shop.core.model.entity.user.Role;
import ru.sibsutis.shop.core.model.entity.user.User;
import ru.sibsutis.shop.core.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found."));
    }

    public User loadUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with this ID not found."));
    }

    public User createUser(RegisterRequestDto request) {
        Role role = Role.valueOf(request.getRole().toUpperCase());
        User user = switch (role) {
            case ADMIN -> User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .build();

            case REGULAR -> Customer.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.REGULAR)
                    .address(request.getAddress())
                    .orders(List.of())
                    .build();
        };
        return userRepository.save(user);
    }
}
