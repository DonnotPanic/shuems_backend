package com.example.shu_ems_server.dto.teacher

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/2
 **/

data class OpenCourseResDto(
    var kh: String?,
    var name: String?,
    var credit: Int?,
    var rate: Float?,
    var departmentName: String?,
    var description: String?,
    var num: Int,
    var capacity: Int,
    var teacherName: String?,
    var gh: String?,
    var courseTime: String?
)

data class OpenCoursesWithSemesterResDto(
    var course: List<OpenCourseResDto>?,
    var xq: String?,
)

class OpenCourselistResDto {
    lateinit var value: List<OpenCoursesWithSemesterResDto>
}
