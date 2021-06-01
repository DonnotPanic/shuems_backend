package com.example.shu_ems_server.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Course : Entity<Course> {
    companion object : Entity.Factory<Course>()

    var kh: String
    var name: String
    var credit: Int
    var department: Department
    var usualFinalRate: Float
    var description: String?
}

object Courses : Table<Course>("course") {
    val kh = varchar("kh").primaryKey().bindTo { it.kh }
    val name = varchar("name").bindTo { it.name }
    val credit = int("credit").bindTo { it.credit }
    val yxh = varchar("yxh").references(Departments) { it.department }
    val usualFinalRate = float("usual_final_rate").bindTo { it.usualFinalRate }
    val description = varchar("description").bindTo { it.description }
}

val Database.courses get() = this.sequenceOf(Courses)
