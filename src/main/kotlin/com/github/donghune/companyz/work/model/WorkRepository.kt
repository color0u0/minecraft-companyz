package com.github.donghune.companyz.work.model

import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File

class WorkRepository(
    override val dataType: Class<Work>,
    override val file: File
) : EntityRepository<Work>() {
    override fun getDefaultData(key: String): Work {
        return Work(
            key,
            "display",
            "description",
            WorkMission("", listOf(), ""),
            WorkType.ADMINISTRATIVE_AFFAIRS,
            WorkReward(
                100,
                100
            )
        )
    }
}