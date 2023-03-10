package com.starnet.musicplayer.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Planeter
 * @description: JWT工具类
 * @date 2021/4/29 20:48
 * @status dev
 */
@Slf4j
@Component
public class JwtUtils {
    /**
     * @return token的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            log.warn("Token Decode Error:{}", token);
            return null;
        }
    }

    /**
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.warn("Token Decode Error:{}", token);
            return null;
        }
    }

    /**
     * 生成签名,expireTime后过期
     *
     * @param username 用户名
     * @param time     过期时间(秒) 9000
     * @return 加密token
     */
    public static String sign(String username, long time) {
        try {
            Date date = new Date(System.currentTimeMillis() + time * 10000);
            Algorithm algorithm = Algorithm.HMAC256("HelloWorld");//暂时固定盐
            // username+date
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)//过期时间
                    .withIssuedAt(new Date())//签发时间
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.warn("Token Sign FAILED");
            return null;
        }
    }

    /**
     * @return token是否过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().before(now);
        }catch(JWTDecodeException e){
            log.warn("Token Decode Error:{}", token);
        }
        return true;
    }

    /**
     * 生成随机盐,长度32位
     */
    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes(16).toHex();
    }

}
