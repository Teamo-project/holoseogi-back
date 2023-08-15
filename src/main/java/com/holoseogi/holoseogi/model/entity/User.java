package com.holoseogi.holoseogi.model.entity;

import com.holoseogi.holoseogi.model.request.OAuth2JoinPlusUserInfo;
import com.holoseogi.holoseogi.type.AuthProvider;
import com.holoseogi.holoseogi.type.UserGender;
import com.holoseogi.holoseogi.type.UserRegion;
import com.holoseogi.holoseogi.type.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String name;

    private String img;

    private String phone;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private UserRegion region;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String refreshToken;

    @OneToMany(mappedBy = "mentor")
    private List<Mentoring> mentorings = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String img, String phone, Integer age, UserRegion region, UserRole role, UserGender gender, AuthProvider authProvider, String refreshToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.img = img;
        this.phone = phone;
        this.age = age;
        this.region = region;
        this.gender = gender;
        this.role = role;
        this.authProvider = authProvider;
        this.refreshToken = refreshToken;
    }

    public void updateOAuth2UserInfo(OAuth2JoinPlusUserInfo dto) {
        this.phone = dto.getPhone();
        this.region = UserRegion.findByLabel(dto.getRegion());
        this.age = dto.getAge();
        this.gender = UserGender.findByLabel(dto.getGender());
        this.role = UserRole.findByLabel(dto.getRole());
    }
}