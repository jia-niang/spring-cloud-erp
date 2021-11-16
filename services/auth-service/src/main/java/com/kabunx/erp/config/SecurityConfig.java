package com.kabunx.erp.config;

import com.kabunx.erp.security.account.AccountAuthenticationProvider;
import com.kabunx.erp.security.mobile.MobileAuthenticationProvider;
import com.kabunx.erp.security.wechat.WechatAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        // 添加认证过滤器
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/auth/**", "/errors/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(wechatAuthenticationProvider())
                .authenticationProvider(mobileAuthenticationProvider())
                .authenticationProvider(accountAuthenticationProvider());
    }

    /**
     * 微信认证授权提供者
     *
     * @return 微信身份验证提供者
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider() {
        return new WechatAuthenticationProvider();
    }

    /**
     * 手机验证码认证授权提供者
     *
     * @return 短信身份验证提供者
     */
    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        return new MobileAuthenticationProvider();
    }

    /**
     * 用户名密码认证授权提供者
     *
     * @return 验证码身份验证提供者
     */
    @Bean
    public AccountAuthenticationProvider accountAuthenticationProvider() {
        return new AccountAuthenticationProvider();
    }

}
