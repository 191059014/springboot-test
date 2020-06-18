package com.hb.test.springsecurity.jwt.jwt;

import com.hb.test.springsecurity.jwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author Mr.Huang
 * @version v0.1, JwtUtils.java, 2020/6/17 15:32, create by huangbiao.
 */
public class JwtUtils {

    /**
     * SUBJECT
     */
    private static final String SUBJECT = "userInfo";

    /**
     * 过期时间，毫秒，一周
     */
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;

    //秘钥
    private static final String APP_SECRET = "secretKey";

    public static void main(String[] args) {
        User user = new User(1L,"zhangsan","123456");
        String token = generateJsonWebToken(user);
        System.out.println(token);
        Claims claims = checkJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成jwt
     *
     * @param user 用户信息
     * @return jwt令牌
     */
    public static String generateJsonWebToken(User user) {
        if (user == null || user.getUserId() == null) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getUserId())
                .claim("name", user.getUserName())
                .setIssuedAt(new Date()) // 设置签发日期
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE)) // 设置过期日期
                .signWith(SignatureAlgorithm.HS256, APP_SECRET).compact();

        return token;
    }


    /**
     * 校验token
     *
     * @param token jwt令牌
     * @return Claims
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
            return claims;

        } catch (Exception e) {
        }
        return null;

    }

}

    