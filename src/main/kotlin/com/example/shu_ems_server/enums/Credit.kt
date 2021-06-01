package com.example.shu_ems_server.enums

/**
 * @Description:
 * @Author: murundong
 * @Date: 2021/5/27
 **/

//    A, 4.0 90
//    AM, 3.7 85
//    BP, 3.3 82
//    B, 3.0 78
//    BM, 2.7 75
//    CP, 2.3 72
//    C, 2.0 68
//    CM, 1.7 66
//    D, 1.5 64
//    DM, 1.0, 60

enum class Credit {
    A, AM, BP, B, BM, CP, C, CM, D, DM, F;

    fun toFloat(): Float {
        return when (this) {
            A -> 4.0.toFloat()
            AM -> 3.7.toFloat()
            BP -> 3.3.toFloat()
            B -> 3.0.toFloat()
            BM -> 2.7.toFloat()
            CP -> 2.3.toFloat()
            C -> 2.0.toFloat()
            CM -> 1.7.toFloat()
            D -> 1.5.toFloat()
            DM -> 1.0.toFloat()
            F -> 0.toFloat()
        }
    }

    fun toRank(): String {
        return this.toString().replace("M", "-").replace("P", "+")
    }
}

fun Int.toCredit(): Credit {
    return when (this) {
        in 90..100 -> Credit.A
        in 85..89 -> Credit.AM
        in 82..84 -> Credit.BP
        in 78..81 -> Credit.B
        in 75..77 -> Credit.BM
        in 72..74 -> Credit.CP
        in 68..71 -> Credit.C
        in 66..67 -> Credit.CM
        in 64..65 -> Credit.D
        in 60..63 -> Credit.DM
        else -> Credit.F
    }
}
