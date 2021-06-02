package com.example.shu_ems_server.controller

import com.example.shu_ems_server.dto.admin.*
import com.example.shu_ems_server.dto.teacher.DeleteOpenCourseReqDto
import com.example.shu_ems_server.service.AdminService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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

    @PostMapping("/addCourses")
    fun addCourses(@RequestBody @Validated courselistReqDto: CourselistReqDto): ResponseEntity<String> {
        return adminService.addCourses(courselistReqDto)
    }

    @PostMapping("/deleteCourse")
    fun deleteCourse(@RequestBody @Validated deleteCourseReqDto: DeleteCourseReqDto): ResponseEntity<String> {
        return adminService.deleteCourse(deleteCourseReqDto.kh)
    }

    @PostMapping("/addDepartments")
    fun addDepartments(@RequestBody @Validated departmentlistReqDto: DepartmentlistReqDto): ResponseEntity<String> {
        return adminService.addDepartments(departmentlistReqDto)
    }

    @PostMapping("/deleteDepartment")
    fun deleteDepartment(@RequestBody @Validated deleteDepartmentReqDto: DeleteDepartmentReqDto): ResponseEntity<String> {
        return adminService.deleteDepartment(deleteDepartmentReqDto.yxh)
    }

    @GetMapping("/semesters")
    fun getSemesters(): SemesterlistResDto {
        return adminService.getSemesters()
    }

    @PostMapping("/updateSemester")
    fun updateSemester(@RequestBody @Validated updateSemesterReqDto: UpdateSemesterReqDto): ResponseEntity<String> {
        return adminService.updateSemester(updateSemesterReqDto)
    }

    @PostMapping("/addSemester")
    fun addSemester(@RequestBody @Validated addSemesterReqDto: AddSemesterReqDto): ResponseEntity<String> {
        return adminService.addSemester(addSemesterReqDto)
    }

    @PostMapping("/deleteSemester")
    fun deleteSemester(@RequestBody @Validated deleteSemesterReqDto: DeleteSemesterReqDto): ResponseEntity<String> {
        return adminService.deleteSemester(deleteSemesterReqDto.xq)
    }

    @PostMapping("/deleteOpenCourse/{teacherId}")
    fun deleteOpenCourse(
        @RequestBody @Validated deleteOpenCourseReqDto: DeleteOpenCourseReqDto,
        @PathVariable teacherId: String
    ): ResponseEntity<String> {
        return adminService.deleteOpenCourse(deleteOpenCourseReqDto, teacherId)
    }

    @PostMapping("/setCourseTime")
    fun setCourseTime(@RequestBody @Validated setCourseTimeReqDto: SetCourseTimeReqDto): ResponseEntity<String> {
        return adminService.setCourseTime(setCourseTimeReqDto)
    }

//    @PostMapping("/expandCourse")
//    fun expandCourse(@RequestBody @Validated)
}
