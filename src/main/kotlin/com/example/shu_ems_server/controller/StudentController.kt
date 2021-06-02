package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.student.selectCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourselistResDto
import com.example.shu_ems_server.service.StudentService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/
@Slf4j
@RestController
@RequestMapping("/student")
class StudentController
@Autowired constructor(
    val studentService: StudentService
) {
    val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/selects")
    fun selectCourse(@RequestBody @Validated selectCourseReqDto: selectCourseReqDto): ResponseEntity<String> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return studentService.selectCourse(selectCourseReqDto, username)
    }

    @GetMapping("/selectedCourse")
    fun getSelectedCourse(): OpenCourselistResDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return studentService.getSelectlist(username)
    }

    @GetMapping("/enrolls")
    fun getEnrolledCourses(): OpenCourselistResDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return studentService.getEnrolllist(username)
    }

//    @GetMapping("/statics")
//    fun getGrades():


}
