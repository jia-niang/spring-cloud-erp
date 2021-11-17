package com.kabunx.erp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabunx.erp.security.filter.JsonUsernamePasswordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 直接跳过filter访问的路由
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/register", "/login/**", "/errors/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(r -> r
                .antMatchers("/auth/login/**").permitAll()
                .anyRequest().authenticated())
                .addFilterAt(jsonUsernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class) // 替换
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
    }

    private JsonUsernamePasswordFilter jsonUsernamePasswordFilter() throws Exception {
        JsonUsernamePasswordFilter filter = new JsonUsernamePasswordFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/auth/login/account");
        return filter;
    }





}
