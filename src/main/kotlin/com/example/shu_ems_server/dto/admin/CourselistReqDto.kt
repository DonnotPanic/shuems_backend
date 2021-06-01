package com.example.shu_ems_server.dto.admin

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/

data class CourseReqDto(
    var kh: String,
    var name: String,
    var credit: Int,
    var rate: Float,
    var yxh: String,
    var description: String?
)

class CourselistReqDto {
    lateinit var value: List<CourseReqDto>
}
