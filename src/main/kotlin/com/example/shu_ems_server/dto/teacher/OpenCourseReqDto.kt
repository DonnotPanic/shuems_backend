package com.example.shu_ems_server.dto.teacher

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/2
 **/
class OpenCourseReqDto {
    lateinit var kh: String
    lateinit var xq: String
    var capacity: Int = 0
    lateinit var course: String
}
