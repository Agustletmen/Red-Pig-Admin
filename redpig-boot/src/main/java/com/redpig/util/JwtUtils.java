package com.redpig.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.redpig.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class JwtUtils {
    private static final String secret = "111111";
    /**
     *  生成JWT token
     */
    public static String token(Authentication authentication){
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000L * 60 * 60 * 24 * 30))  //设置过期时间:单位毫秒
                .withAudience(JSON.toJSONString(authentication)) //设置接受方信息，一般时登录用户
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 根据指定日期返回token
     * @param authentication 认证信息
     * @param time 过期时间 单位毫秒
     * @return 返回token
     */
    public static String token(Authentication authentication,Long time){
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000L * 60 * 60 * 24 * 30))  //设置过期时间:单位毫秒
                .withAudience(JSON.toJSONString(authentication)) //设置接受方信息，一般时登录用户
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证token合法性
     */
    public static void tokenVerify(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        jwtVerifier.verify(token);//没报错说明验证成功

        JWT.decode(token).getExpiresAt();
        String json = JWT.decode(token).getAudience().get(0);

        JwtAuthentication jwtAuthentication = JSON.parseObject(json, JwtAuthentication.class);

        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public static String refreshToken(String token){
        JwtUtils.tokenVerify(token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return JwtUtils.token(authentication);
    }
}
