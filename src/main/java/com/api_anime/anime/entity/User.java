package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;


    @NotBlank(message = "Not null")
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

    @Column(name = "password")
    private String password;

    @Column(name="avatar")
    private String avatar;


    @Column(name = "provider")
    private String provider;


    @Column(name = "token")
    private String token;

    @Column(name= "remember_token")
    private String rememberToken;

    @Column(name = "type_user")
    private String typeUser;


    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
