package com.holoseogi.holoseogi.security.oauth.user;

import com.holoseogi.holoseogi.security.oauth.user.impl.GoogleOAuth2UserInfo;
import com.holoseogi.holoseogi.security.oauth.user.impl.NaverOAuth2UserInfo;
import com.holoseogi.holoseogi.type.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo((Map<String, Object>) attributes.get("response"));
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}