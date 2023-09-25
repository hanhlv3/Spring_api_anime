package com.api_anime.anime.entity;

import com.api_anime.anime.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long userId;


    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    @Email
    private String email;


    @NotBlank(message = "Not found password")
    @Column(nullable = false)
    private String password;

    private String avatar;

    private String provider;



    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = false;

    private Date createdAt;

    private Date updatedAt;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getRealUserName() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
