package net.azeti.challenge.application.infra.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.azeti.challenge.application.infra.security.model.enums.Authority;
import net.azeti.challenge.application.infra.security.model.exception.JwtAuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderImplTest {

    private final String secretKey = "secretKey";
    private final String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getBytes());
    private final String authorizationHeader = "authorizationHeader";
    private final long validityInMinutes = 1000;
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserDetailsService userDetailsServiceImpl;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider(
                secretKey,
                authorizationHeader,
                validityInMinutes,
                userDetailsServiceImpl
        );
    }

    @Test
    void createToken() {
        String username = "username";
        Collection<String> authorities = List.of("authority1", "authority2");

        String token = jwtTokenProvider.createToken(username, authorities);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(token);

        assertThat(claimsJws.getHeader().getAlgorithm()).isEqualTo(SignatureAlgorithm.HS256.name());

        Claims claims = claimsJws.getBody();
        assertThat(claims.getSubject()).isEqualTo(username);
        assertThat(claims.get("authorities")).isEqualTo(authorities);
        assertThat((claims.getExpiration().getTime() - claims.getIssuedAt().getTime()) / 60000)
                .isEqualTo(validityInMinutes);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "validToken",
            "expiredToken",
            "wrongKeyToken",
            "wrongToken"
    })
    void validateToken(String param) {
        String username = "username";
        Collection<String> authorities = List.of("authority1", "authority2");
        String validToken = jwtTokenProvider.createToken(username, authorities);
        Claims claims = Jwts.parser().setSigningKey(secretKeyBase64).parseClaimsJws(validToken).getBody();

        if ("validToken".equals(param)) {
            assertThat(jwtTokenProvider.validateToken(validToken)).isTrue();
        } else if ("expiredToken".equals(param)) {
            claims.setExpiration(new Date());
            String expiredToken = createTokenWithParams(claims, secretKeyBase64);
            assertValidateTokenJwtAuthenticationException(expiredToken);
        } else if ("wrongKeyToken".equals(param)) {
            String wrongSignatureToken = createTokenWithParams(claims, "lulKey");
            assertValidateTokenJwtAuthenticationException(wrongSignatureToken);
        } else if ("wrongToken".equals(param)) {
            assertValidateTokenJwtAuthenticationException(param);
        }
    }

    private String createTokenWithParams(Claims claims, String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private void assertValidateTokenJwtAuthenticationException(String token) {
        assertThatCode(() -> jwtTokenProvider.validateToken(token))
                .isInstanceOf(JwtAuthenticationException.class)
                .hasMessage("JWT token is expired or invalid");
    }

    @Test
    void getUserName() {
        String username = "John Doe";
        Collection<String> authorities = List.of("authority1", "authority2");
        String token = jwtTokenProvider.createToken(username, authorities);

        String userName = jwtTokenProvider.getUserName(token);
        assertThat(userName).isEqualTo("John Doe");
    }

    @Test
    void getAuthentication() {
        String username = "username";
        Collection<String> authoritiesString = List.of("READ", "WRITE");
        String token = jwtTokenProvider.createToken(username, authoritiesString);

        Collection<? extends GrantedAuthority> authorities = List.of(Authority.READ, Authority.WRITE);
        UserDetails userDetails = mock(UserDetails.class);
        doReturn(authorities).when(userDetails).getAuthorities();
        doReturn(userDetails).when(userDetailsServiceImpl).loadUserByUsername(username);

        assertThat(jwtTokenProvider.getAuthentication(token))
                .isEqualTo(new UsernamePasswordAuthenticationToken(userDetails, "", authorities));

        verify(userDetailsServiceImpl).loadUserByUsername(username);
    }

    @Test
    void resolveToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "token";
        doReturn(token).when(request).getHeader(authorizationHeader);

        assertThat(jwtTokenProvider.resolveToken(request)).isEqualTo(token);

        verify(request).getHeader(authorizationHeader);
    }
}