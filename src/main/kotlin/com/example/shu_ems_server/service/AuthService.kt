package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.auth.AddUserDto
import com.example.shu_ems_server.dto.auth.AuthReqDto
import com.example.shu_ems_server.dto.auth.AuthResDto

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

interface AuthService {

    fun login(authReqDto: AuthReqDto) : AuthResDto

    fun refreshToken(token: String): AuthResDto

    fun register(addUserDto: AddUserDto): AuthResDto
}
