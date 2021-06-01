package com.example.shu_ems_server.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDate

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Open : Entity<Open> {
    companion object : Entity.Factory<Open>()

    var xq: String
    var course: Course
    var teacher: Teacher
    var capacity: Int
    var num: Int
    var openDate: LocalDate
    var endDate: LocalDate
    var courseTime: String?
}

object Opens : Table<Open>("open") {
    val xq = varchar("xq").primaryKey().bindTo { it.xq }
    val kh = varchar("kh").primaryKey().references(Courses) { it.course }
    val gh = varchar("gh").primaryKey().references(Teachers) { it.teacher }
    val capacity = int("capacity").bindTo { it.capacity }
    val num = int("num").bindTo { it.num }
    val openDate = date("open_date").bindTo { it.openDate }
    val endDate = date("end_date").bindTo { it.endDate }
    val courseTime = varchar("course_time").bindTo { it.courseTime }
}

val Database.opens get() = this.sequenceOf(Opens)
