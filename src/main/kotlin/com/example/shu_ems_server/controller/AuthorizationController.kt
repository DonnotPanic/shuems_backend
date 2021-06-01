package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.auth.AddUserDto
import com.example.shu_ems_server.dto.auth.AuthReqDto
import com.example.shu_ems_server.dto.auth.AuthResDto
import com.example.shu_ems_server.service.AuthService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/
@Slf4j
@RestController
@RequestMapping("/auth")
class AuthorizationController
@Autowired constructor(val authService: AuthService)
{
    val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/register")
    fun registerController(@RequestBody @Validated addUserDto: AddUserDto): AuthResDto {
        log.info("register")
        return authService.register(addUserDto)
    }

    @PostMapping("/login")
    fun authController(@RequestBody @Validated authReqDto: AuthReqDto): AuthResDto {
        log.info("login")
        return authService.login(authReqDto)
    }

    @RequestMapping("/refresh/{token}")
    fun refreshToken(@PathVariable token: String): AuthResDto {
        return authService.refreshToken(token)
    }
}
