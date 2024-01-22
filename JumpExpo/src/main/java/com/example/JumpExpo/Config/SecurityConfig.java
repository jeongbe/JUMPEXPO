package com.example.JumpExpo.Config;


import com.example.JumpExpo.Controller.join.KakaoController;
import com.example.JumpExpo.DTO.Login.LoginForm;
import com.example.JumpExpo.Entity.user.Users;
import com.example.JumpExpo.Repository.comuser.CompanyRepository;
import com.example.JumpExpo.domain.Role;
import jakarta.jws.soap.SOAPBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//시큐리티 컨피그 권한부여 2024-01-09 맹성우
@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport
@RequiredArgsConstructor
public class SecurityConfig  {

    private final UserDetailsService userDetailsService;

    private final CompanyRepository companyRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private KakaoController kakaoController;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();

    }


    //시큐리티
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        //권한부여
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .headers(headers -> headers.frameOptions(options -> options.disable()))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests //모두의 권한
                                .requestMatchers("/JumpExpo/**","/test","/static/**").permitAll()
                                .requestMatchers("/users/**").hasRole(Role.USER.name())//유저 권한
                                .requestMatchers("/com/**").hasRole(Role.COM.name()) // 기업 권한
                                .requestMatchers("/admin/**","/JumpExpo/**").hasRole(Role.ADMIN.name()) // 관리자 권한
                                .anyRequest().authenticated())
                .formLogin((login) -> login
                        .loginPage("/JumpExpo/Login")
                        .loginProcessingUrl("/JumpExpo/AppLogin")
                        .usernameParameter("user_id")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/login/main", true)
                        .failureUrl("/JumpExpo/Loginfail"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/JumpExpo/main")
                        .invalidateHttpSession(true));
        return http.build();

    }


}
