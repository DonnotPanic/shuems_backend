package com.example.shu_ems_server.dto.admin

import com.example.shu_ems_server.enums.Gender
import java.time.LocalDateTime

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
data class TeacherReqDto(
    var gh: String,
    var name: String,
    var gender: Gender,
    var birth: LocalDateTime,
    var jg: String,
    var tel: String?,
    var title: String,
    var yxh: String,
)

class TeacherlistReqDto {
    lateinit var value: List<TeacherReqDto>
}

