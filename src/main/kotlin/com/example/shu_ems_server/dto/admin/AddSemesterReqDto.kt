package com.example.shu_ems_server.dto.admin

import java.time.LocalDateTime

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/6/1
 **/
class AddSemesterReqDto {
    lateinit var year: LocalDateTime
    lateinit var season: String
}
