package com.api_anime.anime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {


    private String userName;
    private String userEmail;
    private String password;

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", email='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



}
