package com.example.shu_ems_server.dto.teacher

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/

data class CourseResDto(
    var kh: String?,
    var name: String?,
    var credit: Int?,
    var rate: Float?,
    var departmentName: String?,
    var description: String?
)

class CourselistResDto {
    lateinit var value: List<CourseResDto>
}
