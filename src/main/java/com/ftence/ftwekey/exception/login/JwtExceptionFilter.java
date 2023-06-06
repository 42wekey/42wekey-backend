package com.ftence.ftwekey.exception.login;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            chain.doFilter(request, response);

        } catch (NotValidTokenException e) {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/exception/login");
            dispatcher.forward(request, response);
        }
    }
}
