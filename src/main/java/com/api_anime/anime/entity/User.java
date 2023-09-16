package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @NotBlank
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


    @NotBlank(message = "User name is invalid")
    @NotEmpty
    @Column(
            name = "user_name",
            nullable = false,
            unique = true
    )
    private String userName;

    @NotBlank(message = "User email is invalid")
    @Email
    @NotEmpty
    @UniqueElements
    @Column(
            name = "user_email",
            nullable = false,
            unique = true
    )
    private String userEmail;

    @Min(value= 6, message = "Password is than 6 character")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\\\S+$).{6,}$\n", message = "oke")
    @Column(name = "password", length = 60)
    private String password;

    @Column(name="avatar")
    private String avatar;


    @Column(name = "provider")
    private String provider;



    @Column(name = "role")
    private String role;

    @Column(name = "enabled")
    private boolean enabled = false;


    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
