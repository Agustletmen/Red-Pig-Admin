package com.redpig.security.filter;

import cn.hutool.json.JSONUtil;
import com.redpig.common.Constant;
import com.redpig.util.JwtUtils;
import com.redpig.util.RedPigTools;
import com.redpig.util.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登录之前获取token校验:如果有token、再去校验token的合法性:如果没有报错 则认为登录成功
 * 【token是根据SpringSecurity的Authentication生成的,相当于token就是SpringSecurity认证后的凭证】
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  {
        String requestURI = request.getRequestURI();

        //匿名地址直接访问
        if(RedPigTools.contains(requestURI, Constant.annos)){
            filterChain.doFilter(request, response);
            return;
        }

        //获取JWT
        String token = request.getHeader("Authorization");
        log.info("接收到的token:{}",token);
        if (token != null) {
            try {
                JwtUtils.tokenVerify(token);
            }catch (Exception e){
                response.setStatus(200);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONUtil.toJsonStr(Result.error("非法token")));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
