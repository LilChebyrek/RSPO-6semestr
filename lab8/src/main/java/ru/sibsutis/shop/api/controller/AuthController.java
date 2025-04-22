package ru.sibsutis.shop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sibsutis.shop.api.dto.AuthRequestDto;
import ru.sibsutis.shop.api.dto.RegisterRequestDto;
import ru.sibsutis.shop.api.dto.SessionResponseDto;
import ru.sibsutis.shop.core.model.entity.Session;
import ru.sibsutis.shop.core.model.entity.user.User;
import ru.sibsutis.shop.core.service.SessionService;
import ru.sibsutis.shop.core.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<SessionResponseDto> registerUser(@RequestBody RegisterRequestDto request) {
        User user = userService.createUser(request);
        Session session = sessionService.createOrExtendSession(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SessionResponseDto(session.getExpiryDate().toString(),
                        session.getToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<SessionResponseDto> authorizeUser(@RequestBody AuthRequestDto request) {
        User user = userService.loadUserByUsername(request.getUsername());
        Session session = sessionService.createOrExtendSession(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SessionResponseDto(session.getExpiryDate().toString(),
                        session.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> deauthorizeUser(@AuthenticationPrincipal User user) {
        sessionService.terminateSession(user);
        return ResponseEntity.ok().build();
    }
}
