package com.hb.test.springsecurity.jwt.util;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.jwt.common.RedisKeyFactory;
import com.hb.test.springsecurity.jwt.common.ResultCode;
import com.hb.test.springsecurity.jwt.exception.BusinessException;
import com.hb.test.springsecurity.jwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT工具类
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/17 15:32, create by huangbiao.
 */
@Component
public class JwtUtils {

    /**
     * Bearer
     */
    public static final String BEARER = "Bearer ";

    /**
     * token
     */
    public static final String TOKEN = "token";

    /**
     * 密钥
     */
    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * 默认过期时间
     */
    @Value("${jwt.defaultTtl}")
    private long defaultTtl;

    /**
     * 记住我，过期时间
     */
    @Value("${jwt.rememberMeTtl}")
    private long rememberMeTtl;

    /**
     * 字符串类型的redis操作类
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成jwt（也可以和redis一起使用，将token放到redis里面）
     *
     * @param user
     *            用户信息
     * @return jwt令牌
     */
    public String createToken(User user, boolean rememberMe) {

        JwtBuilder jwtBuilder = Jwts.builder()
            // 设置用户标识
            .setId(user.getUserId())
            // jwt所面向的用户
            .setSubject(user.getUsername())
            // 设置签发日期
            .setIssuedAt(new Date())
            // 签名方式，MD5
            .signWith(SignatureAlgorithm.HS256, secretKey);

        long expireTime = rememberMe ? rememberMeTtl : defaultTtl;
        // 设置过期日期
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        String token = jwtBuilder.compact();
        // 将token放进redis缓存
        String jwtKey = RedisKeyFactory.getJwtKey(user.getUserId());
        stringRedisTemplate.opsForValue().set(jwtKey, JSON.toJSONString(user), expireTime, TimeUnit.MILLISECONDS);

        return token;
    }

    /**
     * 校验和解析jwt token
     *
     * @param request
     *            请求
     * @return Claims
     */
    public Claims verifyAndParse(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN);
        if (StringUtils.isEmpty(bearerToken)) {
            throw new BusinessException(ResultCode.TOKEN_IS_NULL);
        }
        if (!bearerToken.startsWith(BEARER)) {
            throw new BusinessException(ResultCode.TOKEN_ILLEGAL);
        }
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerToken.replace(BEARER, "")).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.TOKEN_ILLEGAL);
        }

        return claims;
    }

    /**
     * 根据 jwt 获取用户标识
     *
     * @param request
     *            请求
     * @return 用户名
     */
    public String getUserId(HttpServletRequest request) {
        Claims claims = verifyAndParse(request);
        return claims.getId();
    }

    /**
     * 设置JWT过期
     *
     * @param request
     *            请求
     */
    public void invalid(HttpServletRequest request) {
        String userId = getUserId(request);
        // 从redis中清除JWT
        stringRedisTemplate.delete(RedisKeyFactory.getJwtKey(userId));
    }

}
