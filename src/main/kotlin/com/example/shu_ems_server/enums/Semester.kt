package com.example.shu_ems_server.enums

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/
enum class Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER
}

data class Semester(var year: Int, var season: Season) {
    override fun toString(): String {
        return "$year-$season"
    }
}

fun String.toSemesterOrNull(): Semester? {
    val rawsemester = this.split('-')
    val year = rawsemester[0].toIntOrNull()
    val season = try {
        Season.valueOf(rawsemester[1])
    } catch (e: IllegalArgumentException) {
        return null
    }
    year?.let {
        if (it < 1900 || it > 3000)
            return null
        return Semester(it, season)
    } ?: return null
}
