package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.admin.*
import com.example.shu_ems_server.dto.auth.AddUserDto
import com.example.shu_ems_server.enums.UserRole
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.AdminService
import com.example.shu_ems_server.service.AuthService
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/

@Service
class AdminServiceImpl
@Autowired constructor(
    val database: Database,
    val authService: AuthService
) : AdminService {
    override fun getProfile(id: String): AdminResDto {
        val res = AdminResDto()
        res.id = id
        val admin = database.admins.find { it.userId eq id }
        res.name = admin!!.name
        res.gender = admin.gender.toString()
        res.tel = admin.tel
        return res
    }

    override fun getStudentsList(): StudentlistResDto {
        val res = StudentlistResDto()
        res.value = database.from(Students)
            .leftJoin(Departments, on = Students.yxh eq Departments.yxh)
            .select()
            .orderBy(Students.xh.asc())
            .map { row ->
                StudentResDto(
                    xh = row[Students.xh],
                    name = row[Students.name],
                    gender = row[Students.gender],
                    birth = row[Students.birth],
                    jg = row[Students.jg],
                    tel = row[Students.tel],
                    status = row[Students.status],
                    departmentName = row[Departments.name]
                )
            }
        return res
    }

    override fun addStudents(studentlistReqDto: StudentlistReqDto): ResponseEntity<String> {
        val studentList = studentlistReqDto.value
        val errors: MutableCollection<String> = ArrayList()
        val studentErrors: MutableCollection<String> = ArrayList()
        studentList.forEach(fun(studentReq: StudentReqDto) {
            val existStudent = database.students.find { it.xh eq studentReq.xh }
            val student = Student()
            student.name = studentReq.name
            student.xh = studentReq.xh
            student.birth = studentReq.birth.toLocalDate()
            student.jg = studentReq.jg
            student.tel = studentReq.tel
            student.status = studentReq.status
            student.gender = studentReq.gender
            val department = database.departments.find { it.yxh eq studentReq.yxh }
            if (department != null && existStudent == null) {
                student.department = department
                // register for them
                val addUserDto = AddUserDto()
                addUserDto.role = UserRole.STUDENT.toString()
                addUserDto.password = studentReq.xh
                addUserDto.username = "S" + studentReq.xh
                val res = authService.register(addUserDto)
                // get user_id
                val user = User()
                user.id = res.uuid
                student.user = user
                // add them to students database
                database.students.add(student)
            } else {
                if (department == null) {
                    errors.add(studentReq.yxh)
                }
                if (existStudent != null) {
                    studentErrors.add(studentReq.xh)
                }
            }
        })
        if (!errors.isEmpty())
            return ResponseEntity.badRequest().body("没有学院号 $errors")
        if (!studentErrors.isEmpty())
            return ResponseEntity.badRequest().body("已存在学号 $studentErrors")
        return ResponseEntity.ok("添加成功")
    }

    override fun deleteStudent(xh: String): ResponseEntity<String> {
        val student = database.students.find { it.xh eq xh }
        val id = student?.user?.id
        if (id != null) {
            val user = database.users.find { it.id eq id }
            user?.delete()
        } else {
            // by default, casacade deletion will delete student while deleting user
            student?.delete()
        }
        return ResponseEntity.ok("删除成功")
    }

    override fun getTeachersList(): TeacherlistResDto {
        val res = TeacherlistResDto()
        res.value = database.from(Teachers)
            .leftJoin(Departments, on = Teachers.yxh eq Departments.yxh)
            .select()
            .orderBy(Teachers.gh.asc())
            .map { row ->
                TeacherResDto(
                    gh = row[Teachers.gh],
                    name = row[Teachers.name],
                    gender = row[Teachers.gender],
                    birth = row[Teachers.birth],
                    jg = row[Teachers.jg],
                    tel = row[Teachers.tel],
                    title = row[Teachers.title],
                    departmentName = row[Departments.name]
                )
            }
        return res
    }

    override fun addTeachers(teacherlistReqDto: TeacherlistReqDto): ResponseEntity<String> {
        val teacherList = teacherlistReqDto.value
        val errors: MutableCollection<String> = ArrayList()
        val teacherErrors: MutableCollection<String> = ArrayList()
        teacherList.forEach(fun(teacherReq: TeacherReqDto) {
            val existTeacher = database.teachers.find { it.gh eq teacherReq.gh }
            val teacher = Teacher()
            teacher.name = teacherReq.name
            teacher.gh = teacherReq.gh
            teacher.birth = teacherReq.birth.toLocalDate()
            teacher.jg = teacherReq.jg
            teacher.tel = teacherReq.tel
            teacher.title = teacherReq.title
            teacher.gender = teacherReq.gender
            val department = database.departments.find { it.yxh eq teacherReq.yxh }
            if (department != null && existTeacher == null) {
                teacher.department = department
                // register for them
                val addUserDto = AddUserDto()
                addUserDto.role = UserRole.TEACHER.toString()
                addUserDto.password = teacherReq.gh
                addUserDto.username = "T" + teacherReq.gh
                val res = authService.register(addUserDto)
                // get user_id
                val user = User()
                user.id = res.uuid
                teacher.user = user
                // add them to teachers database
                database.teachers.add(teacher)
            } else {
                if (department == null) {
                    errors.add(teacherReq.yxh)
                }
                if (existTeacher != null) {
                    teacherErrors.add(teacherReq.gh)
                }
            }
        })
        if (!errors.isEmpty())
            return ResponseEntity.badRequest().body("没有学院号 $errors")
        if (!teacherErrors.isEmpty())
            return ResponseEntity.badRequest().body("已存在工号 $teacherErrors")
        return ResponseEntity.ok("添加成功")
    }

    override fun deleteTeacher(gh: String): ResponseEntity<String> {
        val teacher = database.teachers.find { it.gh eq gh }
        val id = teacher?.user?.id
        if (id != null) {
            val user = database.users.find { it.id eq id }
            user?.delete()
        } else {
            // by default, casacade deletion will delete teacher while deleting user
            teacher?.delete()
        }
        return ResponseEntity.ok("删除成功")
    }

    override fun getCoursesList(): CourselistResDto {
        val res = CourselistResDto()
        res.value = database.from(Courses)
            .leftJoin(Departments, on = Courses.yxh eq Departments.yxh)
            .select()
            .orderBy(Courses.kh.asc())
            .map { row ->
                CourseResDto(
                    kh = row[Courses.kh],
                    name = row[Courses.name],
                    credit = row[Courses.credit],
                    rate = row[Courses.usualFinalRate],
                    departmentName = row[Departments.name],
                    description = row[Courses.description]
                )
            }
        return res
    }

    override fun addCourses(courselistReqDto: CourselistReqDto): ResponseEntity<String> {
        val courseList = courselistReqDto.value
        val errors: MutableCollection<String> = ArrayList()
        val courseErrors: MutableCollection<String> = ArrayList()
        courseList.forEach(fun(courseReq: CourseReqDto) {
            val existsCourse = database.courses.find { it.kh eq courseReq.kh }
            val course = Course()
            course.name = courseReq.name
            course.kh = courseReq.kh
            course.credit = courseReq.credit
            course.usualFinalRate = courseReq.rate
            course.description = courseReq.description
            val department = database.departments.find { it.yxh eq courseReq.yxh }
            if (department != null && existsCourse == null) {
                course.department = department
                // add them to courses database
                database.courses.add(course)
            } else {
                if (department == null) {
                    errors.add(courseReq.yxh)
                }
                if (existsCourse != null) {
                    courseErrors.add(courseReq.kh)
                }
            }
        })
        if (!errors.isEmpty())
            return ResponseEntity.badRequest().body("没有学院号 $errors")
        if (!courseErrors.isEmpty())
            return ResponseEntity.badRequest().body("已有课程号 $courseErrors")
        return ResponseEntity.ok("添加成功")
    }

    override fun deleteCourse(kh: String): ResponseEntity<String> {
        val course = database.courses.find { it.kh eq kh }
        course?.delete()
        return ResponseEntity.ok("删除成功")
    }

}
