package com.lys.schedulemanagement.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    // 토큰 만료 시간
    private static final long EXPIRATION_TIME = 60 * 60 * 1000L; // 60분

    private static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;


    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    // JWT 토큰 생성
    public String generatedToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiryDate)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
    }

    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | SignatureException e){
            logger.error("Invalid JWT signature, 유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT token, 만료된 JWT token입니다.");
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            logger.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }


    public void addJwtToCookie(String token, HttpServletResponse response) {
        try{
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            response.addCookie(cookie);
        }catch (UnsupportedEncodingException e){
            logger.error(e.getMessage());
        }
    }
}
