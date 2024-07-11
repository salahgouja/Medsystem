package com.salah.entity;

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
    private LocalDateTime createdAt ;
    private LocalDateTime expiresAt ;
    private LocalDateTime validatedAt ;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user ;

}
