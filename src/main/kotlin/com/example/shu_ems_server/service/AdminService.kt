package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.admin.*
import com.example.shu_ems_server.dto.teacher.DeleteOpenCourseReqDto
import org.springframework.http.ResponseEntity

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/
interface AdminService {

    fun getStudentsList(): StudentlistResDto

    fun addStudents(studentlistReqDto: StudentlistReqDto): ResponseEntity<String>

    fun deleteStudent(xh: String): ResponseEntity<String>

    fun getTeachersList(): TeacherlistResDto

    fun addTeachers(teacherlistReqDto: TeacherlistReqDto): ResponseEntity<String>

    fun deleteTeacher(gh: String): ResponseEntity<String>

    fun addCourses(courselistReqDto: CourselistReqDto): ResponseEntity<String>

    fun deleteCourse(kh: String): ResponseEntity<String>

    fun addDepartments(departmentlistReqDto: DepartmentlistReqDto): ResponseEntity<String>

    fun deleteDepartment(yxh: String): ResponseEntity<String>

    fun getSemesters(): SemesterlistResDto

    fun updateSemester(updateSemesterReqDto: UpdateSemesterReqDto): ResponseEntity<String>

    fun addSemester(addSemesterReqDto: AddSemesterReqDto): ResponseEntity<String>

    fun deleteSemester(xq: String): ResponseEntity<String>

    fun deleteOpenCourse(deleteOpenCourseReqDto: DeleteOpenCourseReqDto, teacherId: String): ResponseEntity<String>

    fun setCourseTime(setCourseTimeReqDto: SetCourseTimeReqDto): ResponseEntity<String>
}
