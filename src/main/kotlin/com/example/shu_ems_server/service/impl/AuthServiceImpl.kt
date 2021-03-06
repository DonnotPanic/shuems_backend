package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.auth.AddUserDto
import com.example.shu_ems_server.dto.auth.AuthReqDto
import com.example.shu_ems_server.dto.auth.AuthResDto
import com.example.shu_ems_server.dto.user.ChangePasswordDto
import com.example.shu_ems_server.enums.UserRole
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.AuthService
import com.example.shu_ems_server.utils.JwtUtil
import lombok.extern.slf4j.Slf4j
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.update
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.security.auth.message.AuthException

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

@Service
@Slf4j
class AuthServiceImpl
@Autowired constructor(
    val database: Database,
    val jwtUtil: JwtUtil,
    val authenticationManager: AuthenticationManager
) : AuthService {
    val log = LoggerFactory.getLogger(this.javaClass)

    private fun generateToken(user: User): AuthResDto {
        val jwtToken = jwtUtil.generateToken(user)
        val resDto = AuthResDto()
        resDto.id = user.name.substring(1)
        resDto.role = user.role
        resDto.uuid = user.id
        val name: String?
        when (user.role) {
            UserRole.TEACHER -> name = database.teachers.find { it.userId eq user.id }?.name
            UserRole.STUDENT -> name = database.students.find { it.userId eq user.id }?.name
            UserRole.ADMIN -> name = database.admins.find { it.userId eq user.id }?.name
        }
        resDto.name = name ?: ""
        resDto.expiration = jwtUtil.getAllClaimsFromToken(jwtToken).expiration.time
        resDto.token = jwtToken
        return resDto
    }

    override fun login(authReqDto: AuthReqDto): AuthResDto {
        val username = authReqDto.role.substring(0, 1) + authReqDto.username
        val token = UsernamePasswordAuthenticationToken(username, authReqDto.password)
        try {
            log.info(token.toString())
            val authentication = authenticationManager.authenticate(token)
            SecurityContextHolder.getContext().authentication = authentication
            val userDetails = authentication.principal as MyUserDetails
            val user = userDetails.user
            return generateToken(user)
        } catch (e: DisabledException) {
            log.warn("forbidden")
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "??????????????????????????????")
        } catch (e: BadCredentialsException) {
            log.warn("wrong info")
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "????????????????????????????????????")
        }
    }

    override fun refreshToken(token: String): AuthResDto {
        try {
            if (jwtUtil.validateToken(token)) {
                val claims = jwtUtil.getAllClaimsFromToken(token)
                val id = claims.subject as String
                val user = database.users.find { it.id eq id }
                return user?.let { generateToken(it) }!!
            }
        } catch (e: Exception) {
            log.warn("Refresh Token Error")
            throw AuthException("invalid")
        }
        val user = User()
        user.name = "mysterious"
        return generateToken(User())
    }

    override fun register(addUserDto: AddUserDto): AuthResDto {
        val user = User()
        user.id = UUID.randomUUID().toString()
        user.name = addUserDto.username
        val encoder = BCryptPasswordEncoder()
        user.password = encoder.encode(addUserDto.password)
        user.role = UserRole.valueOf(addUserDto.role.toUpperCase())
        log.info("User Register: $user")
        database.users.add(user)
        return generateToken(user)
    }

    override fun changePassword(id: String, changePasswordDto: ChangePasswordDto): ResponseEntity<String> {
        val user = database.users.find { it.id eq id } ?: return ResponseEntity.badRequest().build()
        val encoder = BCryptPasswordEncoder()
        if (encoder.encode(changePasswordDto.oldPassword) == user.password) {
            user.password = encoder.encode(changePasswordDto.newPassword)
            database.users.update(user)
        }
        return ResponseEntity.ok("??????????????????")
    }
}
