package ru.sibsutis.shop.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.core.model.entity.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public Session() {
    }

    public Session(User user, String token, LocalDateTime expiryDate) {
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
