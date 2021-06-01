package com.example.shu_ems_server.filter

import com.example.shu_ems_server.utils.JwtUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/

@Slf4j
@Component
class JwtTokenFilter @Autowired constructor(val jwtUtil: JwtUtil) : OncePerRequestFilter() {
    val log = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        try {
            if (!header.isEmpty() && header.startsWith("JWT_TOKEN ")) {
                val token = header.split(" ")[1].trim()
                if (jwtUtil.validateToken(token)) {
                    val claims = jwtUtil.getAllClaimsFromToken(token)
                    val authorities: MutableList<GrantedAuthority> = ArrayList()
                    val role = claims.get("role") as String
                    authorities.add(SimpleGrantedAuthority(role))
                    log.info("current id: " + claims.subject)
                    log.info("current role: $role")
                    val authentication = UsernamePasswordAuthenticationToken(claims.subject, null, authorities)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                } else {
                    throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Jwt 已过期")
                }
            }
        } catch (e: Exception) {
            log.warn("Jwt invalid")
        }
        filterChain.doFilter(request, response)
    }
}
