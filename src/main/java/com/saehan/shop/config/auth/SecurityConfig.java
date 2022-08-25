package com.saehan.shop.config.auth;

import com.saehan.shop.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
//                .csrf().disable().headers().frameOptions().disable()
//                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/h2-consle/**","/images/**" ,"/img/**", "/item/**", "/posts").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.ADMIN.name())
                .antMatchers("/admin/**", "/posts/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();

    }
}
