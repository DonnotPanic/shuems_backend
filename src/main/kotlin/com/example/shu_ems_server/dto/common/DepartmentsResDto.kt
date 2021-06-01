package com.example.shu_ems_server.dto.common

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/31
 **/
data class DepartmentResDto(
    var yxh: String?,
    var name: String?,
    var addr: String?,
    var tel: String?
)

class DepartmentsResDto {
    lateinit var value: List<DepartmentResDto>
}
