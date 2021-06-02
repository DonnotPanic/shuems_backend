package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.teacher.*
import com.example.shu_ems_server.enums.toSemesterOrNull
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.TeacherService
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.or
import org.ktorm.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/2
 **/
@Service
class TeacherServiceImpl
@Autowired constructor(
    val database: Database
) : TeacherService {
    override fun getCoursesList(username: String): CourselistResDto {
        val res = CourselistResDto()
        var coursesList = database.courses.sortedBy { it.kh.asc() }.toList()
        if (username != "") {
            val teacher = database.teachers.find { it.userId eq username }!!
            val yxh = teacher.department.yxh
            coursesList = database.courses.filter { (it.yxh eq yxh) or (it.yxh eq "0000") }
                .sortedBy { it.kh.asc() }.toList()
        }
        res.value = coursesList.map { course ->
            CourseResDto(
                kh = course.kh,
                name = course.name,
                credit = course.credit,
                rate = course.usualFinalRate,
                departmentName = course.department.name,
                description = course.description
            )
        }
        return res
    }

    override fun getOpenCoursesList(username: String): OpenCourselistResDto {
        val openCoursePairs: List<Pair<String, List<Open>>> = if (username != "") {
            val teacher = database.teachers.find { it.userId eq username }!!
            database.opens.filter { it.gh eq teacher.gh }.groupBy { it.semester.xq }.toList()
        } else {
            database.opens.groupBy { it.semester.xq }.toList()
        }

        val res = OpenCourselistResDto()

        val openCourses = openCoursePairs.map {
            val (key, values) = it
            val openCoursesWithSemester = values.map { openCourse ->
                OpenCourseResDto(
                    kh = openCourse.course.kh,
                    name = openCourse.course.name,
                    credit = openCourse.course.credit,
                    rate = openCourse.course.usualFinalRate,
                    departmentName = openCourse.course.department.name,
                    description = openCourse.course.description,
                    num = openCourse.num,
                    capacity = openCourse.capacity,
                    teacherName = openCourse.teacher.name,
                    gh = openCourse.teacher.gh,
                    courseTime = openCourse.courseTime
                )
            }
            OpenCoursesWithSemesterResDto(xq = key, course = openCoursesWithSemester)
        }
        res.value = openCourses
        return res
    }

    override fun openCourse(openCourseReqDto: OpenCourseReqDto, userId: String): ResponseEntity<String> {
        val openCourse = Open()
        val teacher = database.teachers.find { it.userId eq userId }!!
        openCourse.teacher = teacher
        val semester = database.semesters.find { it.xq eq openCourseReqDto.xq }
            ?: return ResponseEntity.badRequest().body("${openCourseReqDto.xq.toSemesterOrNull()}未开放！")
        openCourse.semester = semester
        val course = database.courses.find { it.kh eq openCourseReqDto.kh }
            ?: return ResponseEntity.badRequest().body("课号为${openCourseReqDto.kh}的课程不存在！")
        openCourse.course = course
        openCourse.capacity = openCourseReqDto.capacity
        openCourse.num = 0
        database.opens.add(openCourse)
        return ResponseEntity.ok("开课成功")
    }

    override fun deleteOpenCourse(
        deleteOpenCourseReqDto: DeleteOpenCourseReqDto,
        userId: String
    ): ResponseEntity<String> {
        val teacher = database.teachers.find { it.userId eq userId }!!
        val gh = teacher.gh
        database.opens.removeIf {
            (it.gh eq gh) and (it.kh eq deleteOpenCourseReqDto.kh) and (it.xq eq deleteOpenCourseReqDto.xq)
        }
        return ResponseEntity.ok("删除成功")
    }

}
