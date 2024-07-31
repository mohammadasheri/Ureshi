package com.ureshii.demo.config;


import com.ureshii.demo.authentication.AuthEntryPointJwt;
import com.ureshii.demo.authentication.AuthTokenFilter;
import com.ureshii.demo.authentication.JwtUtils;
import com.ureshii.demo.role.RoleEnum;
import com.ureshii.demo.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig {

    private static final String[] WHITE_LIST = {"swagger-ui/**",
            "v3/api-docs/**",
            "/ws/**",
            "/index.html",
            "/",
            "/user/create",
            "/user/login",
            "/song/download/{id}",
            "/song/picture/download/{id}",
            "/playlist/picture/download/{id}"
    };

    private final UserService userService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;

    public WebSecurityConfig(UserService userService, AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils) {
        this.userService = userService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exp -> exp.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(ex -> ex.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/update").hasAnyAuthority(RoleEnum.Admin.name())

                        .requestMatchers("/song/create").hasAnyAuthority(RoleEnum.Admin.name())
                        .requestMatchers("/song/list").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())
                        .requestMatchers("/song/list/home").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())


                        .requestMatchers("/artist/create").hasAnyAuthority(RoleEnum.Admin.name())
                        .requestMatchers("/artist/{id}").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())
                        .requestMatchers("/artist/findByName").hasAnyAuthority(RoleEnum.Admin.name(),
                                RoleEnum.User.name())
                        .requestMatchers("/artist/list").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())

                        .requestMatchers("/playlist/create").hasAnyAuthority(RoleEnum.Admin.name())
                        .requestMatchers("/playlist/{id}").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())
                        .requestMatchers("/playlist/findByName").hasAnyAuthority(RoleEnum.Admin.name(),
                                RoleEnum.User.name())
                        .requestMatchers("/playlist/list").hasAnyAuthority(RoleEnum.Admin.name(), RoleEnum.User.name())
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }
}
