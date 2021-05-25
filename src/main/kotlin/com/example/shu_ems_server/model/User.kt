package com.example.shu_ems_server.model

import com.example.shu_ems_server.enums.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/

interface User : Entity<User> {
    companion object : Entity.Factory<User>()

    var id: String
    var name: String
    var role: UserRole
    var password: String
}

class MyUserDetails
constructor(val user: User)
    : UserDetails {

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.name
    }

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<SimpleGrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(user.role.toString()))
        return authorities
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }
}

object Users : Table<User>("user") {
    val id = varchar("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val role = enum<UserRole>("role").bindTo { it.role }
    val password = varchar("password").bindTo { it.password }
}

val Database.users get() = this.sequenceOf(Users)
