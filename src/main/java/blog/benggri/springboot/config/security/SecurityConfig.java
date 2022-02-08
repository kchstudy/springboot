package blog.benggri.springboot.config.security;

import blog.benggri.springboot.config.jwt.JwtAccessDeniedHandler;
import blog.benggri.springboot.config.jwt.JwtAuthenticationEntryPoint;
import blog.benggri.springboot.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http.csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/","/auth/**","/swagger/**","/swagger-resources/**","/webjars/**","/v2/api-docs","/v3/api-docs","/swagger-ui/**", "/api/v2/**", "/api/v3/**","/health" ).permitAll()
            .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
            .and()
            .apply(new JwtSecurityConfig(tokenProvider));
    }
}
