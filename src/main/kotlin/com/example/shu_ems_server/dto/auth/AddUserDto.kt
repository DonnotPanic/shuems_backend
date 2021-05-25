package com.example.shu_ems_server.dto.auth

import lombok.Data

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

@Data
class AddUserDto {

    lateinit var username: String

    lateinit var password: String

    lateinit var role: String
}
