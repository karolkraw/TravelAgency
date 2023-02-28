package com.example.travelagency.security;

/*
@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests()
                .mvcMatchers("/userOnly/**").permitAll()
                .anyRequest().permitAll();
        http.csrf().disable()
                .authorizeHttpRequests()
                //.requestMatchers("/registration/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/destinations/**").hasAuthority("aaa")
                .requestMatchers(HttpMethod.POST, "/destinations/**").permitAll()
                //.requestMatchers("/destinations/get/1").hasRole("ADMIN")
                //.requestMatchers("http://localhost:8080/destinations/update/1").hasAnyRole()
                //.authorizeHttpRequests().requestMatchers("/**").permitAll()

                .anyRequest().authenticated()
                .and().formLogin()
                .and().authenticationManager(authenticationManager);
        return http.build();


        */
/*http.cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .securityMatcher("/ws/**")
                .authorizeHttpRequests(autorizeRequests -> autorizeRequests
                        .requestMatchers(HttpMethod.GET, "/ws/healthz", "/ws/ready", "/ws/version").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/ws/user/**",
                                "/ws/user/avatar/*",
                                "/ws/user/search").hasAnyAuthority("SCOPE_tmt:user")
                        .requestMatchers(HttpMethod.POST,
                                "/ws/friend",
                                "/ws/user/trip",
                                "/ws/trip/*").hasAnyAuthority("SCOPE_tmt:user")
                ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();*//*

    }

}
*/

import com.example.travelagency.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .and()
                .authorizeRequests()
                .antMatchers("/destinations/**").hasRole("USER")
                .antMatchers("/guides/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
}