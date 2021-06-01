package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.admin.*
import org.springframework.http.ResponseEntity

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/
interface AdminService {
    fun getProfile(id: String): AdminResDto

    fun getStudentsList(): StudentlistResDto

    fun addStudents(studentlistReqDto: StudentlistReqDto): ResponseEntity<String>

    fun deleteStudent(xh: String): ResponseEntity<String>

    fun getTeachersList(): TeacherlistResDto

    fun addTeachers(teacherlistReqDto: TeacherlistReqDto): ResponseEntity<String>

    fun deleteTeacher(gh: String): ResponseEntity<String>

    fun getCoursesList(): CourselistResDto

    fun addCourses(courselistReqDto: CourselistReqDto): ResponseEntity<String>

    fun deleteCourse(kh: String): ResponseEntity<String>

}
