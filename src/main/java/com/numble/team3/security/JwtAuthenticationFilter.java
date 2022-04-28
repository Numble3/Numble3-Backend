package com.numble.team3.security;

import com.numble.team3.jwt.TokenHelper;
import com.numble.team3.sign.infra.RedisUtils;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final CustomUserDetailsService userDetailsService;
  private final RedisUtils redisUtils;
  private final TokenHelper accessTokenHelper;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    extractToken(request).filter(token -> redisUtils.validToken(token, "accessToken",
        Optional.ofNullable(
          accessTokenHelper.parse(token).map(claims -> claims.getAccountId()).orElse(null))))
      .map(userDetailsService::loadUserByUsername).ifPresent(this::setAuthentication);
    chain.doFilter(request, response);
  }

  private void setAuthentication(CustomUserDetails userDetails) {
    SecurityContextHolder.getContext()
      .setAuthentication(new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
  }

  private Optional<String> extractToken(ServletRequest request) {
    return Arrays.stream(
        Optional.ofNullable(((HttpServletRequest) request).getCookies()).orElse(new Cookie[]{}))
      .filter(cookie -> cookie.getName().equals("accessToken"))
      .map(cookie -> URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8)).findFirst();
  }
}