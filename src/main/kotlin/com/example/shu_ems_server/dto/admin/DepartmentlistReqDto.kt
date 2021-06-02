package com.example.shu_ems_server.dto.admin;

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/
data class DepartmentReqDto(
    var yxh: String,
    var name: String,
    var addr: String,
    var tel: String?
)

class DepartmentlistReqDto {
    lateinit var value: List<DepartmentReqDto>
}
