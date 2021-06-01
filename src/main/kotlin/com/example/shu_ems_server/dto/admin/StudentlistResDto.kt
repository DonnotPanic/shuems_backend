package com.example.shu_ems_server.dto.admin

import com.example.shu_ems_server.enums.Gender
import java.time.LocalDate

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/

data class StudentResDto(
    var xh: String?,
    var name: String?,
    var gender: Gender?,
    var birth: LocalDate?,
    var jg: String?,
    var tel: String?,
    var status: String?,
    var departmentName: String?,
)

class StudentlistResDto {
    lateinit var value: List<StudentResDto>
}
