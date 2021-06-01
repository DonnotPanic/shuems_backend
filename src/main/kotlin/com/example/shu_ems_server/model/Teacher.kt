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

interface Teacher : Entity<Teacher> {
    companion object : Entity.Factory<Teacher>()

    var gh: String
    var name: String
    var tel: String?
    var gender: Gender
    var birth: LocalDate
    var jg: String
    var title: String
    var department: Department
    var user: User
}

object Teachers : Table<Teacher>("teacher") {
    val gh = varchar("gh").primaryKey().bindTo { it.gh }
    val name = varchar("name").bindTo { it.name }
    val tel = varchar("tel").bindTo { it.tel }
    val gender = enum<Gender>("gender").bindTo { it.gender }
    val birth = date("birth").bindTo { it.birth }
    val jg = varchar("jg").bindTo { it.jg }
    val title = varchar("title").bindTo { it.title }
    val yxh = varchar("yxh").references(Departments) { it.department }
    val userId = varchar("user_id").references(Users) { it.user }
}

val Database.teachers get() = this.sequenceOf(Teachers)
