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

data class SemesterEnum(var year: Int, var season: Season) {
    override fun toString(): String {
        val quarter = when (season) {
            Season.AUTUMN -> "秋季学期"
            Season.WINTER -> "冬季学期"
            Season.SPRING -> "春季学期"
            Season.SUMMER -> "夏季学期"
        }
        return "$year-${year + 1}年$season"
    }
}

fun String.toSemesterOrNull(): SemesterEnum? {
    val rawsemester = this.split('-')
    val year = rawsemester[0].toIntOrNull() ?: return null
    val season = when (rawsemester[1]) {
        "Q1" -> Season.AUTUMN
        "Q2" -> Season.WINTER
        "Q3" -> Season.SPRING
        "Q4" -> Season.SUMMER
        else -> null
    } ?: return null

    if (year < 1900 || year > 3000)
        return null
    return SemesterEnum(year, season)

}
