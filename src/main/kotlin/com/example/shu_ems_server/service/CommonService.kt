package com.example.shu_ems_server.service

import com.example.shu_ems_server.dto.common.DepartmentsResDto

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
interface CommonService {
    fun getDepartments(): DepartmentsResDto
}
