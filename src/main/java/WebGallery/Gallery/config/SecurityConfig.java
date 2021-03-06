package WebGallery.Gallery.config;

import WebGallery.Gallery.util.JwTokenProvider2;
import WebGallery.Gallery.util.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwTokenProvider2 jwTokenProvider2;
    private final RedisTemplate redisTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/guest/**").hasAnyRole("GUEST","AUTHOR")
                .antMatchers("/author/**").hasRole("AUTHOR")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwTokenProvider2,redisTemplate),
                        UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.httpFirewall(defaultHttpFireWall());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // BcryptPasswordEncoder??? BCrypt?????? ?????? ????????? ???????????? ??????????????? ??????????????? ???????????????.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall defaultHttpFireWall(){
        return new DefaultHttpFirewall();
    }
}
