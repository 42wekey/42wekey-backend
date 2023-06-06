package com.ftence.ftwekey.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.exception.login.NotValidTokenException;
import com.ftence.ftwekey.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

@Slf4j
@Service
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String tokenSecret;

    @Autowired
    private UserRepository userRepository;

    public String makeAuthToken(Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        LocalDateTime localDateTime = LocalDateTime.now();
        int sec = JwtProperties.EXPIRATION_TIME / 1000;    // 1일
        localDateTime = localDateTime.plusSeconds(sec);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date expireDate = Date.from(localDateTime.atZone(defaultZoneId).toInstant());

        ArrayList array = (ArrayList) oAuth2User.getAttributes().get("cursus_users");
        LinkedHashMap object = (LinkedHashMap) array.get(1);

        String token = JWT.create()
                .withSubject(oAuth2User.getAttributes().get("login").toString())
                .withClaim("id", Long.parseLong(oAuth2User.getAttributes().get("id").toString()))
                .withClaim("level", (double) object.get("level"))
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(tokenSecret));

        return token;
    }

    public VerifyResult verifyToken(String token) {

        String sub = null;
        Long id = 0L;
        double level = 0;

        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(tokenSecret)).build().verify(token);

            sub = verify.getClaim("sub").asString();
            id = verify.getClaim("id").asLong();
            level = verify.getClaim("level").asDouble();

            User user = userRepository.findByUniqueId(id);

            if (!user.getIntraId().equals(sub))
                throw (new NoResultException());

            return VerifyResult.builder()
                    .success(true)
                    .uniqueId(id)
                    .intraId(sub)
                    .level(level)
                    .build();

        } catch (Exception e) {

            log.error("JWT 인증 실패. [{}]  sub={}, id={}, level={}, token={}", e, sub, id, level, token);
            throw new NotValidTokenException(e.getClass().getSimpleName());
        }
    }
}
