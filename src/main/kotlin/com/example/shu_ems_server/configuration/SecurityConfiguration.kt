package com.example.shu_ems_server.configuration

import com.example.shu_ems_server.filter.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Bean
    fun UserDetailsService(): UserDetailsService {
        return super.userDetailsService()
    }

    @Autowired
    lateinit var jwtTokenFilter: JwtTokenFilter

    @Bean
    fun PasswordEncoderBean(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(PasswordEncoderBean())
    }

    override fun configure(http: HttpSecurity?) {
        http!!.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/teacher/opens").hasAnyAuthority("STUDENT", "ADMIN", "TEACHER")
            .antMatchers("/teacher/**").hasAnyAuthority("TEACHER", "ADMIN")
            .antMatchers("/student/**").hasAnyAuthority("STUDENT", "ADMIN")
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .antMatchers("/commmon/**").hasAnyAuthority("STUDENT", "ADMIN", "TEACHER")
            .anyRequest().authenticated()

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.headers().cacheControl()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }


}
