package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.admin.*
import com.example.shu_ems_server.service.AdminService
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
@RequestMapping("/admin")
class AdminController
@Autowired constructor(val adminService: AdminService) {
    val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/profile")
    fun getProfile(): AdminResDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = principal.toString()
        return adminService.getProfile(username)
    }

    @GetMapping("/teachers")
    fun getTeachersList(): TeacherlistResDto {
        return adminService.getTeachersList()
    }

    @PostMapping("/addTeachers")
    fun addTeachers(@RequestBody @Validated teacherlistReqDto: TeacherlistReqDto): ResponseEntity<String> {
        return adminService.addTeachers(teacherlistReqDto)
    }

    @PostMapping("/deleteTeacher")
    fun deleteTeacher(@RequestBody @Validated deleteTeacherReqDto: DeleteTeacherReqDto): ResponseEntity<String> {
        return adminService.deleteTeacher(deleteTeacherReqDto.gh)
    }

    @GetMapping("/students")
    fun getStudentsList(): StudentlistResDto {
        return adminService.getStudentsList()
    }

    @PostMapping("/addStudents")
    fun addStudents(@RequestBody @Validated studentlistReqDto: StudentlistReqDto): ResponseEntity<String> {
        return adminService.addStudents(studentlistReqDto)
    }

    @PostMapping("/deleteStudent")
    fun deleteStudent(@RequestBody @Validated deleteStudentReqDto: DeleteStudentReqDto): ResponseEntity<String> {
        return adminService.deleteStudent(deleteStudentReqDto.xh)
    }

    @GetMapping("/courses")
    fun getcoursesList(): CourselistResDto {
        return adminService.getCoursesList()
    }

    @PostMapping("/addCourses")
    fun addcourses(@RequestBody @Validated courselistReqDto: CourselistReqDto): ResponseEntity<String> {
        return adminService.addCourses(courselistReqDto)
    }

    @PostMapping("/deleteCourse")
    fun deletecourse(@RequestBody @Validated deletecourseReqDto: DeleteCourseReqDto): ResponseEntity<String> {
        return adminService.deleteCourse(deletecourseReqDto.kh)
    }
}
