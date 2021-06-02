package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.teacher.CourselistResDto
import com.example.shu_ems_server.dto.teacher.DeleteOpenCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourselistResDto
import com.example.shu_ems_server.enums.UserRole
import com.example.shu_ems_server.service.TeacherService
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
 * @Date: 2021/5/26
 **/
@Slf4j
@RestController
@RequestMapping("/teacher")
class TeacherController
@Autowired constructor(
    val teacherService: TeacherService
) {
    val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/courses")
    fun getCoursesList(): CourselistResDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val roles = SecurityContextHolder.getContext().authentication.authorities.toList()
        if (roles[0].toString() == "ADMIN")
            return teacherService.getCoursesList("")
        val username = principal.toString()
        return teacherService.getCoursesList(username)
    }

    @GetMapping("/opens")
    fun getOpenCoursesList(): OpenCourselistResDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        val roles = SecurityContextHolder.getContext().authentication.authorities.toList()
        if (roles[0].toString() != UserRole.TEACHER.toString())
            return teacherService.getOpenCoursesList("")
        return teacherService.getOpenCoursesList(username)
    }

    @PostMapping("/openCourse")
    fun openCourse(@RequestBody @Validated openCourseReqDto: OpenCourseReqDto): ResponseEntity<String> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return teacherService.openCourse(openCourseReqDto, username)
    }

    @PostMapping("/deleteOpenCourse")
    fun deleteOpenCourse(@RequestBody @Validated deleteOpenCourseReqDto: DeleteOpenCourseReqDto): ResponseEntity<String> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return teacherService.deleteOpenCourse(deleteOpenCourseReqDto, username)
    }

//    @GetMapping("/getClasses")
//    fun getClasses()

//    @PostMapping("/setGrades")
//    fun setGrades(@RequestBody @Validated)

}
