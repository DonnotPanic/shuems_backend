package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.teacher.CourselistResDto
import com.example.shu_ems_server.dto.teacher.DeleteOpenCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourselistResDto
import org.springframework.http.ResponseEntity

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/
interface TeacherService {
    fun getCoursesList(username: String): CourselistResDto

    fun getOpenCoursesList(username: String): OpenCourselistResDto

    fun openCourse(openCourseReqDto: OpenCourseReqDto, userId: String): ResponseEntity<String>

    fun deleteOpenCourse(deleteOpenCourseReqDto: DeleteOpenCourseReqDto, userId: String): ResponseEntity<String>
}
