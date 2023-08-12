package com.holoseogi.holoseogi.model.request;

import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.AuthProvider;
import com.holoseogi.holoseogi.type.UserRole;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateUserReq {
    private String email;
    private String password;
    private String name;
    private String role;

    public User toEntity(String encodingPassword) {
        return User.builder()
                .email(email)
                .password(encodingPassword)
                .name(name)
                .role(UserRole.findByLabel(role))
                .authProvider(AuthProvider.GENERAL)
                .build();
    }
}
