package com.salah.token;

import com.salah.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Entity(name ="token" )
public class Token {
    @Id
    @GeneratedValue
    private Long id ;
    private String token ;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    private LocalDateTime createdAt ;
    private LocalDateTime expiresAt ;
    private LocalDateTime validatedAt ;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user ;

}
