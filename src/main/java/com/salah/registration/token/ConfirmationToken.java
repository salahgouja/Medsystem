package com.salah.registration.token;

import com.salah.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    //nullable
    private String token;

    //nullable
    private LocalDateTime createdAt;

    //nullable
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;
    //nullable
    @ManyToOne
    @JoinColumn(

            name = "app_user_id"
    )
    private User User;


}
