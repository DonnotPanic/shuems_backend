package com.example.shu_ems_server.model

import com.example.shu_ems_server.enums.Credit
import com.example.shu_ems_server.enums.toCredit
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import kotlin.math.roundToInt

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Grade : Entity<Grade> {
    companion object : Entity.Factory<Grade>()

    var student: Student
    var semester: Semester
    var course: Course
    var usualGrade: Int?
    var finalGrade: Int?
    fun getGrade(): Int? {
        return usualGrade?.let {
            val u = it
            finalGrade?.let {
                val r = course.usualFinalRate
                (u * r + u * (1 - r)).roundToInt()
            }
        }
    }

    fun getCredit(): Credit? {
        return getGrade()?.toCredit()
    }
}

object Grades : Table<Grade>("grade") {
    val xh = varchar("xh").primaryKey().references(Students) { it.student }
    val xq = varchar("xq").primaryKey().references(Semesters) { it.semester }
    val kh = varchar("kh").primaryKey().references(Courses) { it.course }
    val usualGrade = int("usual_grade").bindTo { it.usualGrade }
    val finalGrade = int("final_grade").bindTo { it.finalGrade }
}
