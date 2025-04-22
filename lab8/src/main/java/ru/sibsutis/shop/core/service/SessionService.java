package ru.sibsutis.shop.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.core.model.entity.Session;
import ru.sibsutis.shop.core.model.entity.user.User;
import ru.sibsutis.shop.core.repository.SessionRepository;
import ru.sibsutis.shop.core.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final JwtUtil jwtUtil;

    private final SessionRepository sessionRepository;

    @Transactional
    public Session createOrExtendSession(User user) {
        String token = jwtUtil.generateToken(user.getId());
        LocalDateTime expiryDate = jwtUtil.extractExpiryDate(token);

        Optional<Session> optionalSession = sessionRepository.findByUser(user);
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            session.setToken(token);
            session.setExpiryDate(expiryDate);
            return sessionRepository.save(session);
        }

        Session session = new Session(user, token, expiryDate);
        return sessionRepository.save(session);
    }

    @Transactional
    public void terminateSession(User user) {
        Session session = sessionRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Session with this token not found."));

        session.setActive(false);
        sessionRepository.save(session);
    }
}
