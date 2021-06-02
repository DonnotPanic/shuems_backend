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
interface Selection : Entity<Selection> {
    companion object : Entity.Factory<Selection>()

    var student: Student
    var open: Open
}

object Selections : Table<Selection>("selection") {
    val xh = varchar("xh").primaryKey().references(Students) { it.student }
    val openId = int("open_id").primaryKey().references(Opens) { it.open }
}

val Database.selections get() = this.sequenceOf(Selections)
