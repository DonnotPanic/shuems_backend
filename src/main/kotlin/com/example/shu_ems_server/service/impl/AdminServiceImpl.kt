package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.admin.*
import com.example.shu_ems_server.dto.auth.AddUserDto
import com.example.shu_ems_server.dto.teacher.DeleteOpenCourseReqDto
import com.example.shu_ems_server.enums.Season
import com.example.shu_ems_server.enums.SemesterEnum
import com.example.shu_ems_server.enums.UserRole
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.AdminService
import com.example.shu_ems_server.service.AuthService
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.desc
import org.ktorm.dsl.eq
import org.ktorm.entity.*
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

    override fun getStudentsList(): StudentlistResDto {
        val res = StudentlistResDto()
        res.value = database.students.sortedBy { it.xh.asc() }.toList()
            .map { student ->
                StudentResDto(
                    xh = student.xh,
                    name = student.name,
                    gender = student.gender,
                    birth = student.birth,
                    jg = student.jg,
                    tel = student.tel,
                    status = student.status,
                    departmentName = student.department.name
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
        res.value = database.teachers.sortedBy { it.gh.asc() }.toList()
            .map { teacher ->
                TeacherResDto(
                    gh = teacher.gh,
                    name = teacher.name,
                    gender = teacher.gender,
                    birth = teacher.birth,
                    jg = teacher.jg,
                    tel = teacher.tel,
                    title = teacher.title,
                    departmentName = teacher.department.name
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

    override fun addDepartments(departmentlistReqDto: DepartmentlistReqDto): ResponseEntity<String> {
        val departmentList = departmentlistReqDto.value
        val errors: MutableCollection<String> = ArrayList()
        departmentList.forEach(fun(departmentReq: DepartmentReqDto) {
            val existsDepartment = database.departments.find { it.yxh eq departmentReq.yxh }
            val department = Department()
            department.yxh = departmentReq.yxh
            department.name = departmentReq.name
            department.addr = departmentReq.addr
            department.tel = departmentReq.tel

            if (existsDepartment == null) {
                database.departments.add(department)
            } else {
                errors.add(departmentReq.yxh)
            }
        })
        if (!errors.isEmpty())
            return ResponseEntity.badRequest().body("已有学院号 $errors")
        return ResponseEntity.ok("添加成功")
    }

    override fun deleteDepartment(yxh: String): ResponseEntity<String> {
        val course = database.courses.find { it.yxh eq yxh }
        course?.delete()
        return ResponseEntity.ok("删除成功")
    }

    override fun getSemesters(): SemesterlistResDto {
        val res = SemesterlistResDto()
        res.value = database.semesters.sortedBy { it.xq.desc() }.toList()
        return res
    }

    override fun updateSemester(updateSemesterReqDto: UpdateSemesterReqDto): ResponseEntity<String> {
        val semester =
            database.semesters.find { it.xq eq updateSemesterReqDto.xq } ?: return ResponseEntity.badRequest()
                .body("找不到学期${updateSemesterReqDto.xq}")
        if (semester.status == "C") {
            return ResponseEntity.badRequest().body("成绩已发布的学期状态无法更改")
        }
        if (semester.status == "O" && updateSemesterReqDto.status != "O") {
            val opens = database.opens.filter { it.xq eq updateSemesterReqDto.xq }.toList()
            opens.forEach { it ->
                val openId = it.id
                val capacity = it.capacity
                val selections = database.selections.filter { it.openId eq openId }
                    .sortedBy { it.xh.desc() }.take(capacity).toList()
                database.selections.removeIf { it.openId eq openId }
                selections.forEach {
                    val enroll = Enroll()
                    enroll.open = it.open
                    enroll.student = it.student
                    database.enrolls.add(enroll)
                }
            }
        }
        if (semester.status != updateSemesterReqDto.status) {
            semester.status = updateSemesterReqDto.status
            semester.flushChanges()
        }
        return ResponseEntity.ok("修改成功")
    }

    override fun addSemester(addSemesterReqDto: AddSemesterReqDto): ResponseEntity<String> {
        val year = addSemesterReqDto.year.toLocalDate().year
        val season = Season.valueOf(addSemesterReqDto.season)
        val semesterEnum = SemesterEnum(year, season)
        val semester = Semester()
        semester.xq = semesterEnum.toString()
        semester.status = "O"
        val existSemester = database.semesters.find { it.xq eq semester.xq }
        return if (existSemester == null) {
            database.semesters.add(semester)
            ResponseEntity.ok("添加成功")
        } else {
            ResponseEntity.badRequest().body("该学期已存在")
        }
    }

    override fun deleteSemester(xq: String): ResponseEntity<String> {
        val semester = database.semesters.find { it.xq eq xq }
        semester?.delete()
        return ResponseEntity.ok("删除成功")
    }

    override fun deleteOpenCourse(
        deleteOpenCourseReqDto: DeleteOpenCourseReqDto,
        teacherId: String
    ): ResponseEntity<String> {
        val teacher = database.teachers.find { it.gh eq teacherId }!!
        val gh = teacher.gh
        database.opens.removeIf {
            (it.gh eq gh) and (it.kh eq deleteOpenCourseReqDto.kh) and (it.xq eq deleteOpenCourseReqDto.xq)
        }
        return ResponseEntity.ok("删除成功")
    }

    override fun setCourseTime(setCourseTimeReqDto: SetCourseTimeReqDto): ResponseEntity<String> {
        val open =
            database.opens.find { (it.kh eq setCourseTimeReqDto.kh) and (it.gh eq setCourseTimeReqDto.gh) and (it.xq eq setCourseTimeReqDto.xq) }
                ?: return ResponseEntity.badRequest()
                    .body("所找的开课（${setCourseTimeReqDto.gh}, ${setCourseTimeReqDto.kh}, ${setCourseTimeReqDto.xq}）不存在")
        open.courseTime = setCourseTimeReqDto.courseTime
        database.opens.update(open)
        return ResponseEntity.ok("修改成功")
    }
}
