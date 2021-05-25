package com.example.shu_ems_server.dto.auth

import lombok.Data
import org.jetbrains.annotations.NotNull

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

@Data
class AuthReqDto {

    lateinit var username : String

    lateinit var password : String
}
