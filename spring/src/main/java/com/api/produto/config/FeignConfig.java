package com.api.produto.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {
    /* Este arquivo é para passar o token de autorização para o próximo microserviço,
    pois ao fazer a requisição ao produto ele valida o id do cliente e se não passar
    o token de autorização ele retorna erro 401 */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String authorizationHeader = request.getHeader("Authorization");
                    if (authorizationHeader != null) {
                        // Repassa o token para a chamada Feign
                        template.header("Authorization", authorizationHeader);
                    }
                }
            }
        };
    }
}
