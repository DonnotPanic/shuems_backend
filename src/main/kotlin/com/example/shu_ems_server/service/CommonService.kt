package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.admin.SemesterlistResDto
import com.example.shu_ems_server.dto.common.DepartmentsResDto
import com.example.shu_ems_server.dto.user.UserProfileResDto
import com.example.shu_ems_server.enums.UserRole

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
interface CommonService {
    fun getProfile(id: String, role: UserRole): UserProfileResDto

    fun getDepartments(): DepartmentsResDto

    fun getSemesters(): SemesterlistResDto
}
