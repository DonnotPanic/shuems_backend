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
interface Enroll : Entity<Enroll> {
    companion object : Entity.Factory<Enroll>()

    var student: Student
    var open: Open
}

object Enrolls : Table<Enroll>("enroll") {
    val xh = varchar("xh").primaryKey().references(Students) { it.student }
    val openId = varchar("open_id").primaryKey().references(Opens) { it.open }
}

val Database.enrolls get() = this.sequenceOf(Enrolls)
