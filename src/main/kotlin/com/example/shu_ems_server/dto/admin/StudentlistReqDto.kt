package com.example.shu_ems_server.dto.admin

import com.example.shu_ems_server.enums.Gender
import java.time.LocalDateTime

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
data class StudentReqDto(
    var xh: String,
    var name: String,
    var gender: Gender,
    var birth: LocalDateTime,
    var jg: String,
    var tel: String?,
    var status: String?,
    var yxh: String,
)

class StudentlistReqDto {
    lateinit var value: List<StudentReqDto>
}
