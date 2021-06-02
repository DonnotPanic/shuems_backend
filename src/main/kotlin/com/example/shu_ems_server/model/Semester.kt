package com.example.shu_ems_server.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/
interface Semester : Entity<Semester> {
    companion object : Entity.Factory<Semester>()

    var xq: String
    var status: String
}

object Semesters : Table<Semester>("semester") {
    val xq = varchar("xq").primaryKey().bindTo { it.xq }
    val status = varchar("status").bindTo { it.status }
}

val Database.semesters get() = this.sequenceOf(Semesters)
