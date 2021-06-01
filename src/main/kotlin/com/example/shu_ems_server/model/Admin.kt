package com.example.shu_ems_server.model

import com.example.shu_ems_server.enums.Gender
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.varchar

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/

interface Admin : Entity<Admin> {
    companion object : Entity.Factory<Admin>()

    var name: String
    var gender: Gender
    var tel: String
    var user: User
}

object Admins : Table<Admin>("admin") {
    val userId = varchar("id").primaryKey().references(Users) { it.user }
    val name = varchar("name").bindTo { it.name }
    val gender = enum<Gender>("gender").bindTo { it.gender }
    val tel = varchar("tel").bindTo { it.tel }
}

val Database.admins get() = this.sequenceOf(Admins)
