package com.example.shu_ems_server.dto.admin

import lombok.Data

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/30
 **/
@Data
class AdminResDto {

    lateinit var id: String

    lateinit var name: String

    lateinit var gender: String

    lateinit var tel: String
}
