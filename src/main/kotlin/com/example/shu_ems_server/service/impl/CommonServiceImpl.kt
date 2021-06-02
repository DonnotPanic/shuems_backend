package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.admin.SemesterlistResDto
import com.example.shu_ems_server.dto.common.DepartmentResDto
import com.example.shu_ems_server.dto.common.DepartmentsResDto
import com.example.shu_ems_server.dto.user.UserProfileResDto
import com.example.shu_ems_server.enums.UserRole
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.CommonService
import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.sortedBy
import org.ktorm.entity.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
@Service
class CommonServiceImpl
@Autowired constructor(
    val database: Database,
) : CommonService {
    override fun getProfile(id: String, role: UserRole): UserProfileResDto {
        val res = UserProfileResDto()
        res.id = id
        res.role = when (role) {
            UserRole.STUDENT -> "学生"
            UserRole.TEACHER -> "教师"
            UserRole.ADMIN -> "管理员"
        }
        when (role) {
            UserRole.ADMIN -> {
                val admin = database.admins.find { it.userId eq id }!!
                res.name = admin.name
                res.gender = admin.gender.toString()
                res.tel = admin.tel
            }
            UserRole.TEACHER -> {
                val teacher = database.teachers.find { it.userId eq id }!!
                res.name = teacher.name
                res.gender = teacher.gender.toString()
                res.tel = teacher.tel.toString()
                res.departmentName = teacher.department.name
            }
            UserRole.STUDENT -> {
                val student = database.students.find { it.userId eq id }!!
                res.name = student.name
                res.gender = student.gender.toString()
                res.tel = student.tel.toString()
                res.departmentName = student.department.name
            }
        }
        return res
    }

    override fun getDepartments(): DepartmentsResDto {
        val res = DepartmentsResDto()
        res.value = database.departments.sortedBy { it.yxh.asc() }.toList()
            .map { department ->
                DepartmentResDto(
                    yxh = department.yxh,
                    name = department.name,
                    addr = department.addr,
                    tel = department.tel
                )
            }
        return res
    }

    override fun getSemesters(): SemesterlistResDto {
        val res = SemesterlistResDto()
        res.value = database.semesters.filter { it.status eq "O" }.toList()
        return res
    }
}
