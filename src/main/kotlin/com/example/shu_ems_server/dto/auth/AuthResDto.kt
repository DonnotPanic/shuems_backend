package com.example.shu_ems_server.dto.auth

import com.example.shu_ems_server.enums.UserRole
import lombok.Data

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

@Data
class AuthResDto {

    lateinit var token : String

    var expiration: Long = 0

    lateinit var name : String

    lateinit var id : String

    lateinit var role : UserRole
}
