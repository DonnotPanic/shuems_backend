package com.example.shu_ems_server.configuration


import org.ktorm.database.Database
import org.ktorm.jackson.KtormModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/8
 **/

@Configuration
class KtormConfiguration {
    @Autowired
    lateinit var dataSource: DataSource;

    @Bean
    fun dataBase(): Database {
        return Database.connectWithSpringSupport(dataSource);
    }

    /* serde */
    @Bean
    fun ktormModule(): KtormModule {
        return KtormModule()
    }
}
