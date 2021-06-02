package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.student.selectCourseReqDto
import com.example.shu_ems_server.dto.teacher.OpenCourseResDto
import com.example.shu_ems_server.dto.teacher.OpenCourselistResDto
import com.example.shu_ems_server.dto.teacher.OpenCoursesWithSemesterResDto
import com.example.shu_ems_server.model.*
import com.example.shu_ems_server.service.StudentService
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/3
 **/
@Service
class StudentServiceImpl
@Autowired constructor(
    val database: Database,
) : StudentService {
    override fun selectCourse(selectCourseReqDto: selectCourseReqDto, userId: String): ResponseEntity<String> {
        val selection = Selection()
        val open =
            database.opens.find { (it.xq eq selectCourseReqDto.xq) and (it.kh eq selectCourseReqDto.kh) and (it.gh eq selectCourseReqDto.gh) }
                ?: return ResponseEntity.badRequest().body("没有这个课程！")
        val exists = database.selections.find { it.openId eq open.id }
        if (exists != null) return ResponseEntity.badRequest().body("课程已选中！")
        val student = database.students.find { it.userId eq userId }!!
        selection.open = open
        selection.student = student
        database.selections.add(selection)
        return ResponseEntity.ok("选课成功！")
    }

    override fun getSelectlist(username: String): OpenCourselistResDto {
        val student = database.students.find { it.userId eq username }!!
        val opens =
            database.selections.filter { it.xh eq student.xh }.map { it.open }.groupBy { it.semester.xq }.toList()
        val res = OpenCourselistResDto()

        val openCourses = opens.map {
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

    override fun getEnrolllist(username: String): OpenCourselistResDto {
        val student = database.students.find { it.userId eq username }!!
        val opens = database.enrolls.filter { it.xh eq student.xh }.map { it.open }.groupBy { it.semester.xq }.toList()
        val res = OpenCourselistResDto()

        val openCourses = opens.map {
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


}
