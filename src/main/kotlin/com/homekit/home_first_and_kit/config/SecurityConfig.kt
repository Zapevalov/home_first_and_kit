package com.homekit.home_first_and_kit.config

import com.homekit.home_first_and_kit.security.jwt.JwtConfigurer
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager

import org.springframework.security.core.userdetails.UserDetailsService




@Configuration
class SecurityConfig @Autowired constructor(val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null)
            throw SecurityException("Http security configuration is null")
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(REGISTER_ENDPOINT).permitAll()
            .antMatchers(LOGIN_ENDPOINT).permitAll()
            .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .apply(JwtConfigurer(jwtTokenProvider))
    }

    companion object{
        const val ADMIN_ENDPOINT = "/api/v1/admin/**"
        const val LOGIN_ENDPOINT = "/api/v1/auth/login"
        const val REGISTER_ENDPOINT = "/api/v1/user/register"
    }
}