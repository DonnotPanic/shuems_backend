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

interface Department : Entity<Department> {
    companion object : Entity.Factory<Department>()

    var yxh: String
    var name: String
    var addr: String
    var tel: String?
}

object Departments : Table<Department>("department") {
    val yxh = varchar("yxh").primaryKey().bindTo { it.yxh }
    val name = varchar("name").bindTo { it.name }
    val addr = varchar("addr").bindTo { it.addr }
    val tel = varchar("tel").bindTo { it.tel }
}

val Database.departments get() = this.sequenceOf(Departments)
