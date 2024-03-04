//package config;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebMvc
//
//public class SecurityConfig {
//    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
//    private final UserAuthenticationProvider userAuthenticationProvider;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling().authenticationEntryPoint((AuthenticationEntryPoint) userAuthenticationEntryPoint)
//                .and()
//                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
//                        .anyRequest().authenticated())
//        ;
//        return http.build();
//    }
//}
