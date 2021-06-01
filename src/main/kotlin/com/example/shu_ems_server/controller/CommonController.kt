package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.common.DepartmentsResDto
import com.example.shu_ems_server.dto.user.ChangePasswordDto
import com.example.shu_ems_server.service.AuthService
import com.example.shu_ems_server.service.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/
@RestController
@RequestMapping("/common")
class CommonController
@Autowired constructor(
    val authService: AuthService,
    val commonService: CommonService,
) {
    @PostMapping("/changePassword")
    fun changePassword(@RequestBody @Validated changePasswordDto: ChangePasswordDto): ResponseEntity<String> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return authService.changePassword(principal.toString(), changePasswordDto)
    }

    @GetMapping("/departments")
    fun getDepartments(): DepartmentsResDto {
        return commonService.getDepartments()
    }
}
