package com.example.shu_ems_server.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Select : Entity<Select> {
    companion object : Entity.Factory<Select>()

    var student: Student
    var course: Course
    var teacher: Teacher
    var xq: String
    var status: String
}

object Selects : Table<Select>("select") {
    val xh = varchar("xh").primaryKey().references(Students) { it.student }
    val kh = varchar("kh").primaryKey().references(Courses) { it.course }
    val gh = varchar("gh").primaryKey().references(Teachers) { it.teacher }
    val xq = varchar("xq").primaryKey().bindTo { it.xq }
    val status = varchar("status").bindTo { it.status }
}

val Database.selects get() = this.sequenceOf(Selects)
