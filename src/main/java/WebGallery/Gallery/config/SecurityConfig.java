package WebGallery.Gallery.config;

import WebGallery.Gallery.service.impl.UserDetailsServiceImpl;
import WebGallery.Gallery.util.JwtAuthenticationFilter;
import WebGallery.Gallery.util.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/guest/**").hasRole("GUEST") // USER, ADMIN만 접근 가능
                .antMatchers("/author/**").hasRole("AUTHOR")
                .antMatchers("/admin/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().permitAll() // 누구나 접근 허용
//                .and()
//                .formLogin()
//                .loginPage("/login") // 로그인 페이지 링크
//                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedPage("/denied")
        ;
        http.csrf().ignoringAntMatchers().disable().headers().frameOptions().disable();
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // BcryptPasswordEncoder는 BCrypt라는 해시 함수를 이용하여 패스워드를 암호화하는 구현체이다.
        return new BCryptPasswordEncoder();
    }

}
