package com.holoseogi.holoseogi.init;

import com.holoseogi.holoseogi.type.AuthProvider;
import com.holoseogi.holoseogi.type.UserRole;
import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final UserRepository userRepository;

    @PostConstruct
    public void createDebugUser() {
        User admin = User.builder()
                .name("admin")
                .role(UserRole.ADMIN)
                .email("admin@gmail.com")
                .img("https://lh3.googleusercontent.com/a/AAcHTtexYsEPgy_IbUzA79tynRDUjzCgfabVWcyoBoJsM5R5=s96-c")
                .authProvider(AuthProvider.GOOGLE)
                .build();
        userRepository.save(admin);
    }
}
