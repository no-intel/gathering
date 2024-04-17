package org.noint.gathering.config;

import lombok.RequiredArgsConstructor;
import org.noint.gathering.config.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
@Profile("!local-test")
public class InterceptorConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/member", "/member/login");
    }
}
