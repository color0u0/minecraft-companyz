package com.github.donghune.companyz.work.model

enum class WorkState(
    val message: String
){
    PENDING("아직 모집중인 아르바이트 입니다."),
    ACCEPTED("채용이 마감된 아르바이트 입니다"),
    COMPLETE("공고가 만료된 아르바이트 입니다")
}