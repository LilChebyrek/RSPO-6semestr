package ru.sibsutis.shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibsutis.shop.core.model.entity.Session;
import ru.sibsutis.shop.core.model.entity.user.User;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    Optional<Session> findByUser(User user);
}
