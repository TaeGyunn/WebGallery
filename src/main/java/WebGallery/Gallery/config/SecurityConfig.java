package WebGallery.Gallery.config;

import WebGallery.Gallery.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final GuestService guestService;

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
                .and()
                .formLogin()
                .loginPage("/login") // 로그인 페이지 링크
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
                .and()
                .exceptionHandling().accessDeniedPage("/denied")
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(guestService).passwordEncoder(new BCryptPasswordEncoder());
    }



}
