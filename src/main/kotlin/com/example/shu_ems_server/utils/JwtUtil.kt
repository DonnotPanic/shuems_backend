package com.example.shu_ems_server.utils

import com.example.shu_ems_server.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey


/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/

@Component
class JwtUtil @Autowired constructor(
    @Value("\${jwt.secret}") val secret: String?,
    @Value("\${jwt.expiration}") val expiration: String?)
{
    private var secretKey: SecretKey? = null;

    @PostConstruct
    fun init() {
        this.secretKey = Keys.hmacShaKeyFor(secret!!.toByteArray())
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getAllClaimsFromToken(token).expiration
        return expiration.before(Date())
    }


    private fun doGenerateToken(claims: HashMap<String, Any>, id: String): String {
        val expirationTimeLong = expiration?.toLong()

        val createDate = Date()
        val expirationDate = Date(createDate.time + (expirationTimeLong?.times(1000) ?: 0))

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact()
    }

    fun generateToken(user: User): String {
        val claims = HashMap<String, Any>()
        claims.put("role", user.role)
        return doGenerateToken(claims, user.id)
    }

    fun validateToken(token: String): Boolean {
        return !isTokenExpired(token);
    }

    fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).body
    }
}
