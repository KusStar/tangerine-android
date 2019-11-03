package com.kuss.tangerine.util.constants

import kotlin.random.Random

class DefaultText() {
    val texts = listOf<String>(
        "Frank Ocean",
        "Bon Iver",
        "Pink Floyd",
        "Talos",
        "Rhye",
        "The Antlers",
        "Aquilo",
        "The Wombats",
        "Birdy",
        "Clario",
        "Childish Gambino"
    )
    val text = texts[Random.nextInt(0, texts.size - 1)]
}