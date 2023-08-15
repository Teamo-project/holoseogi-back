package com.holoseogi.holoseogi.security.handler;

import com.holoseogi.holoseogi.exception.BadRequestException;
import com.holoseogi.holoseogi.security.CustomUserDetails;
import com.holoseogi.holoseogi.security.jwt.JwtTokenProvider;
import com.holoseogi.holoseogi.security.oauth.CookieAuthorizationRequestRepository;
import com.holoseogi.holoseogi.service.RedisService;
import com.holoseogi.holoseogi.type.UserRole;
import com.holoseogi.holoseogi.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//    @Value("${app.oauth2.authorizedRedirectUri}")
//    private String redirectUri;
    private final JwtTokenProvider tokenProvider;
    private final CookieAuthorizationRequestRepository authorizationRequestRepository;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response); // 인증에 사용한 쿠키 정보 삭제
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
//        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new BadRequestException("redirect URIs are not matched");
//        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl()); // url 없으면 localhost:8080으로

        if(authentication.getAuthorities().stream().findFirst().get().getAuthority().equals(UserRole.BEFORE.getRole())){
            // 추가 정보 적는 페이지로 이동하도록
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("userId", ((CustomUserDetails)authentication.getPrincipal()).getId())
                    .build().toUriString();
        } else {
            // JWT 생성
            String accessToken = tokenProvider.createAccessToken((CustomUserDetails)authentication.getPrincipal(), authentication.getAuthorities());
            // 로그아웃 체크를 위해 redis 저장
            redisService.setValues("JWT_TOKEN:"+((CustomUserDetails)authentication.getPrincipal()).getId(), accessToken);
            tokenProvider.createRefreshToken(authentication, response);
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("accessToken", accessToken)
                    .build().toUriString();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

//    /**
//     * 내가 설정한 redirect URL과 다른지 확인하는 메서드
//     * */
//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//        URI authorizedUri = URI.create(redirectUri);
//
//        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                && authorizedUri.getPort() == clientRedirectUri.getPort()) {
//            return true;
//        }
//        return false;
//    }
}