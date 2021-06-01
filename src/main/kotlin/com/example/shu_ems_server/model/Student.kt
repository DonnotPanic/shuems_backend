package com.example.shu_ems_server.model

import com.example.shu_ems_server.enums.Gender
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.enum
import org.ktorm.schema.varchar
import java.time.LocalDate

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
interface Student : Entity<Student> {
    companion object : Entity.Factory<Student>()

    var xh: String
    var name: String
    var gender: Gender
    var birth: LocalDate
    var jg: String
    var tel: String?
    var department: Department
    var status: String?
    var user: User?
}

object Students : Table<Student>("student") {
    val xh = varchar("xh").primaryKey().bindTo { it.xh }
    val name = varchar("name").bindTo { it.name }
    val gender = enum<Gender>("gender").bindTo { it.gender }
    val birth = date("birth").bindTo { it.birth }
    val jg = varchar("jg").bindTo { it.jg }
    val tel = varchar("tel").bindTo { it.tel }
    val yxh = varchar("yxh").references(Departments) { it.department }
    val status = varchar("status").bindTo { it.status }
    val userId = varchar("user_id").references(Users) { it.user }
}

val Database.students get() = this.sequenceOf(Students)
