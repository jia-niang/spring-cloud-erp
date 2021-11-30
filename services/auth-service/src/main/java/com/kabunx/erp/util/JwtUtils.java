package com.kabunx.erp.util;

import com.kabunx.erp.property.JwtProperties;
import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Resource
    JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generate(UserVO userVo, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userVo.getId());
        return generateToken(claims, userVo.getAccount(), type);
    }

    private String generateToken(Map<String, Object> claims, String subject, String type) {
        long expirationTimeLong;
        if (SecurityConstant.AUTHORIZATION_ACCESS_TYPE.equals(type)) {
            expirationTimeLong = Long.parseLong(jwtProperties.getExpirationTime()) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(jwtProperties.getExpirationTime()) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
