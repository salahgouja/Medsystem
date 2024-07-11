package com.salah.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@EqualsAndHashCode
@ToString
@Entity(name ="User" )
@EntityListeners(AuditingEntityListener.class)
@Table(name= "users",
        uniqueConstraints = {
                @UniqueConstraint(name="user_unique_email", columnNames = "email")
                }
        )

@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails , Principal  {
    @Id
    @SequenceGenerator(
                         name = "user_id_sequence",
                         sequenceName = "user_id_sequence",
                         allocationSize = 1
                        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "user_id_sequence")

    @Column(name = "id",
            updatable = false)
    private Long id;
    @Column(name = "firstname",
    nullable = false,
    columnDefinition = "Text")
    private String firstname;
    @Column(name = "lastname",
            nullable = false,
            columnDefinition = "Text")
    private String lastname;
    @Column(name = "email",
            nullable = false,
            columnDefinition = "Text"
            )
    private String email;
    @Column(name = "password",
            nullable = false,
            columnDefinition = "Text"
    )
    private String password;
    @Column(name = "phone",

            columnDefinition = "Text"
            )
    private String phone;
    @Column(name = "gender",
            columnDefinition = "Text")
    private String gender;
    @Column(name = "image")
    private String image;
    @Column(name = "title")
    private String title;
    @Column(name = "birthdate" )
    private Date birthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    @Column(name = "accountLocked")
    private Boolean accountLocked;
    @Column(name = "enabled")
    private Boolean enabled;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate ;



    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullName(){
        return firstname + " " + lastname ;
    }

    public User(String firstname, String lastname, String email, String password, Boolean accountLocked, Boolean enabled ,UserRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountLocked = accountLocked;
        this.enabled = enabled;
    }
}
