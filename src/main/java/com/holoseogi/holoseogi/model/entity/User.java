package com.holoseogi.holoseogi.model.entity;

import com.holoseogi.holoseogi.model.request.OAuth2JoinPlusUserInfo;
import com.holoseogi.holoseogi.type.AuthProvider;
import com.holoseogi.holoseogi.type.UserRegion;
import com.holoseogi.holoseogi.type.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Integer phone;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private UserRegion region;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String refreshToken;

    @OneToMany(mappedBy = "mentor")
    private List<Mentoring> mentorings = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String img, UserRole role, AuthProvider authProvider, String refreshToken) {
        this.email = email;
        this.password = (Objects.isNull(password))? "":password;
        this.name = name;
        this.img = img;
        this.role = role;
        this.authProvider = authProvider;
        this.refreshToken = refreshToken;
    }

    public void updateOAuth2UserInfo(OAuth2JoinPlusUserInfo dto) {
        this.phone = dto.getPhone();
        this.region = UserRegion.findByLabel(dto.getAge());
        this.age = dto.getPhone();
        this.role = UserRole.USER;
    }
}