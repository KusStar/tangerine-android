package com.kuss.tangerine.util.helper

import com.kuss.tangerine.R
import com.kuss.tangerine.util.constants.TaskIconMapping
import kotlin.random.Random

class TaskType {
    companion object {
        fun getType(text: String): Int {
            var type: Int? = null
            TaskIconMapping.ids.forEachIndexed { index, id ->
                val isMatched = text.contains(TaskIconMapping.names[index]) || text.contains(TaskIconMapping.alias[index])
                if (isMatched && type == null) {
                    type = id
                }
            }
            type?.let {
                return it
            }
            return TaskIconMapping.ids[Random.nextInt(0, TaskIconMapping.ids.size - 1)]
        }
    }

}