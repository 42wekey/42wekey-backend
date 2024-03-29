package com.ftence.ftwekey.config.jwt;

import com.ftence.ftwekey.config.auth.PrincipalDetails;
import com.ftence.ftwekey.constant.ErrorMessage;
import com.ftence.ftwekey.constant.JwtProperties;
import com.ftence.ftwekey.entity.User;
import com.ftence.ftwekey.exception.login.HeaderException;
import com.ftence.ftwekey.exception.login.NotValidTokenException;
import com.ftence.ftwekey.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, RuntimeException {

        HttpServletRequest req = (HttpServletRequest) request;
        String bearer = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearer == null) {

            chain.doFilter(request, response);
        } else if (!bearer.startsWith(JwtProperties.TOKEN_PREFIX)) {

            log.error("Authorization Header error. AUTHORIZATION 헤더={}", bearer);

            throw new HeaderException(ErrorMessage.AUTHORIZATION_HEADER_MESSAGE);
        } else {

            validHeader(request, response, chain, bearer);
        }
    }

    private void validHeader(ServletRequest request, ServletResponse response, FilterChain chain, String bearer) {

        try {

            String jwtToken = bearer.substring(JwtProperties.TOKEN_PREFIX.length());
            VerifyResult result = jwtUtil.verifyToken(jwtToken);


            log.info("JWT 인증 성공. user={}, uniqueId={}, level={}", result.getIntraId(), result.getUniqueId(), result.getLevel());

            Authentication authentication = getAuthentication(result);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (NotValidTokenException e) {

            throw e;
        }
        catch (ServletException | IOException e) {

            log.error("[{}] {}", getClass().getSimpleName(), e.getMessage());

            throw new NotValidTokenException(e.getMessage());
        }
    }

    private Authentication getAuthentication(VerifyResult result) {

        User user = userRepository.findByIntraId(result.getIntraId());

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRoleKey()));

        return new UsernamePasswordAuthenticationToken(new PrincipalDetails(user), null, roles);
    }
}
