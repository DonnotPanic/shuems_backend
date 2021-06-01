package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.dto.common.DepartmentResDto
import com.example.shu_ems_server.dto.common.DepartmentsResDto
import com.example.shu_ems_server.model.Departments
import com.example.shu_ems_server.service.CommonService
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
@Service
class CommonServiceImpl
@Autowired constructor(
    val database: Database
) : CommonService {
    override fun getDepartments(): DepartmentsResDto {
        val res = DepartmentsResDto()
        res.value = database.from(Departments)
            .select()
            .orderBy(Departments.yxh.asc())
            .map { row ->
                DepartmentResDto(
                    yxh = row[Departments.yxh],
                    name = row[Departments.name],
                    addr = row[Departments.addr],
                    tel = row[Departments.tel]
                )
            }
        return res
    }
}
