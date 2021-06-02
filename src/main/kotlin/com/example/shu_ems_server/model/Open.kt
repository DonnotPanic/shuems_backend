package com.example.shu_ems_server.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Open : Entity<Open> {
    companion object : Entity.Factory<Open>()

    var id: Int
    var semester: Semester
    var course: Course
    var teacher: Teacher
    var capacity: Int
    var num: Int
    var courseTime: String?
}

object Opens : Table<Open>("open") {
    val id = int("id").primaryKey().bindTo { it.id }
    val xq = varchar("xq").references(Semesters) { it.semester }
    val kh = varchar("kh").references(Courses) { it.course }
    val gh = varchar("gh").references(Teachers) { it.teacher }
    val capacity = int("capacity").bindTo { it.capacity }
    val num = int("num").bindTo { it.num }
    val courseTime = varchar("course_time").bindTo { it.courseTime }
}

val Database.opens get() = this.sequenceOf(Opens)
