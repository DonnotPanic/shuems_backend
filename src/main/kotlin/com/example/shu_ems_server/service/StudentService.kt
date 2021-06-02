package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.student.selectCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourselistResDto
import org.springframework.http.ResponseEntity

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/3
 **/
interface StudentService {
    fun selectCourse(selectCourseReqDto: selectCourseReqDto, userId: String): ResponseEntity<String>

    fun getSelectlist(username: String): OpenCourselistResDto

    fun getEnrolllist(username: String): OpenCourselistResDto
}
