package web.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("BCryptPasswordEncoder call=====================>");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Security FilterChain=======================>");
        http.csrf((auth)->auth.disable())
                .formLogin((auth)->auth.disable())
                .httpBasic((auth)->auth.disable());

        // 권한 허용 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/user", "/login","/todo", "/todo/**").permitAll()
                .requestMatchers("/swagger-ui", "/swagger-ui/**",
                        "/api/logistics","/api/swagger-config","/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
