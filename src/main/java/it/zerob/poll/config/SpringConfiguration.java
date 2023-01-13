package it.zerob.poll.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringConfiguration implements WebMvcConfigurer {

    final UserDetailsService userDetailService;
    @Value("${allow-origin.address}")
    private String address;

    @Value("${allow-origin.port}")
    private String port;

    public SpringConfiguration(@Qualifier("usersDetailsService") UserDetailsService userDetailsService) {
        this.userDetailService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    private AuthEntryPointJwt authEntryPointJwt;
//
//    @Bean
//    public AuthTokenFilter getAuthTokenFilter() {
//        return new AuthTokenFilter();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/" ).permitAll()
//                .requestMatchers("/**").hasRole("ADMIN")
//                .and().formLogin().loginPage("/login").failureUrl("/login")
//                .defaultSuccessUrl("/index.html").loginProcessingUrl("/login")
//                .and().logout().logoutSuccessUrl("/login")
                .and().csrf().disable();
//                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .cors(Customizer.withDefaults());
//        http.addFilterBefore(getAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(address + ":" + port));
        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, "content-type"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTION", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}