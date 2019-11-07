package com.kuss.tangerine.util.helper

import com.kuss.tangerine.util.constants.EmojiMapping
import kotlin.random.Random

class EmojiHelper {
    companion object {
        fun getType(text: String): Int {
            var type: Int? = null
            EmojiMapping.ids.forEachIndexed { index, id ->
                val isMatched = text.contains(EmojiMapping.names[index]) || text.contains(EmojiMapping.alias[index])
                if (isMatched && type == null) {
                    type = id
                }
            }
            type?.let {
                return it
            }
            return EmojiMapping.ids[Random.nextInt(0, EmojiMapping.ids.size - 1)]
        }
    }

}