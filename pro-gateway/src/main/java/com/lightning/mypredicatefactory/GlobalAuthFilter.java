package com.lightning.mypredicatefactory;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(0)
public class GlobalAuthFilter implements GlobalFilter, Ordered {
    @Value("${token.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)) {
            Map<String, Object> map = new HashMap() {
                //语法糖
                {
                    put("code", 0);
                    put("msg", "没有登录");
                }
            };
            return response(response, map);
        } else {
            //解析token, 校验token的时间有效性
            if (!isTokenValidate(token)) {
                Map<String, Object> map = new HashMap() {
                    {
                        put("code", 0);
                        put("msg", "token无效");
                    }
                };
                return response(response, map);
            } else {
                return chain.filter(exchange);  //放行请求
            }
        }
    }

    private boolean isTokenValidate(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            Date expiration = claims.getExpiration();  //约定的过期时间
            Date now = new Date();

            if (expiration == null || expiration.before(now)) {
                return false;
            } else {
                System.out.println("Token 有效");
                System.out.println("用户名: " + claims.get("name"));
                System.out.println("过期时间: " + expiration);
                System.out.println("产生日间: " + claims.get("iat"));
                return true;
            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token 已过期！");
        } catch (JwtException e) {
            e.printStackTrace();
            System.out.println("Token 非法！");
        }
        return false;
    }

    //出错的情况下的响应
    private Mono<Void> response(ServerHttpResponse response, Object msg) {
        response.getHeaders().add("Content-Type", "application/json;charset=utf-8");
        Gson g = new Gson();
        String resjson = g.toJson(msg);
        DataBuffer dataBuffer = response.bufferFactory().wrap(resjson.getBytes());
        return response.writeWith(Flux.just(dataBuffer));  //响应json数据
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
