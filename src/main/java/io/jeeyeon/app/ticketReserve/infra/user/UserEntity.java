package io.jeeyeon.app.ticketReserve.infra.user;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "APP_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "BALANCE")
    private Integer balance;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

//    @Version
    private int version;

    public UserEntity(Long userId) {
        this.userId = userId;
        this.balance = 0;
    }

    public UserEntity(User user) {
        this.userId = user.getUserId();
        this.balance = user.getBalance();
        this.version = user.getVersion();
    }

    public User toUser() {
        User user = new User();
        user.setUserId(this.userId);
        user.setBalance(this.balance);
        user.setVersion(this.version);
        return user;
    }

}
