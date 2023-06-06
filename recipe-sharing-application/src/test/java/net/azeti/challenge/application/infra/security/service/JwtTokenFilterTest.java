package net.azeti.challenge.application.infra.security.service;

import net.azeti.challenge.application.infra.security.model.exception.JwtAuthenticationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void doFilter() throws ServletException, IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "token";
        doReturn(token).when(jwtTokenProvider).resolveToken(any());
        doReturn(true).when(jwtTokenProvider).validateToken(token);

        Authentication authentication = mock(Authentication.class);
        doReturn(authentication).when(jwtTokenProvider).getAuthentication(token);

        assertThatCode(() -> jwtTokenFilter.doFilter(servletRequest, servletResponse, filterChain))
                .doesNotThrowAnyException();

        verify(jwtTokenProvider).resolveToken(servletRequest);
        verify(jwtTokenProvider).validateToken(token);
        verify(jwtTokenProvider).getAuthentication(token);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    void doFilterJwtAuthenticationException() throws ServletException, IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "token";
        doReturn(token).when(jwtTokenProvider).resolveToken(any());
        doReturn(true).when(jwtTokenProvider).validateToken(token);

        JwtAuthenticationException exception = new JwtAuthenticationException("Error message", HttpStatus.UNAUTHORIZED);
        doThrow(exception).when(jwtTokenProvider).getAuthentication(token);

        assertThatCode(() -> jwtTokenFilter.doFilter(servletRequest, servletResponse, filterChain))
                .isInstanceOf(JwtAuthenticationException.class)
                .hasMessage("JWT token is expired or invalid");

        verify(jwtTokenProvider).resolveToken(servletRequest);
        verify(jwtTokenProvider).validateToken(token);
        verify(jwtTokenProvider).getAuthentication(token);
        verify(servletResponse).sendError(exception.getHttpStatus().value());
        verify(filterChain, never()).doFilter(any(), any());
    }
}