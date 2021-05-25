package com.example.shu_ems_server.service.impl

import com.example.shu_ems_server.model.MyUserDetails
import com.example.shu_ems_server.model.User
import com.example.shu_ems_server.model.users
import lombok.extern.slf4j.Slf4j
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/25
 **/

@Service
class UserDetailsServiceImpl
@Autowired constructor (val database: Database)
    : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = database.users.find { it.name eq username }
        user ?. let {
            return MyUserDetails(user)
        } ?: run {
            throw UsernameNotFoundException("User Not Exists.")
        }
    }
}
