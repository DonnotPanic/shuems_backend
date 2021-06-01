package com.example.shu_ems_server.dto.admin

import com.example.shu_ems_server.enums.Gender
import java.time.LocalDate

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
data class TeacherResDto(
    var gh: String?,
    var name: String?,
    var gender: Gender?,
    var birth: LocalDate?,
    var jg: String?,
    var tel: String?,
    var title: String?,
    var departmentName: String?,
)

class TeacherlistResDto {
    lateinit var value: List<TeacherResDto>
}
