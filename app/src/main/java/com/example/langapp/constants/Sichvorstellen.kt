package com.example.langapp.constants

import com.example.langapp.data.entities.WordEntity

object Sichvorstellen {
    val wordList = listOf(
        WordEntity(catId = 2, name = "ich", transl = "я", transcr = "[ɪç]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "heiße", transl = "зовусь", transcr = "[ˈhaɪ̯sə]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "bin", transl = "есть", transcr = "[bɪn]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "komme", transl = "приезжаю", transcr = "[ˈkɔmə]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "wohne", transl = "живу", transcr = "[ˈvoːnə]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "aus", transl = "из", transcr = "[aʊ̯s]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "in", transl = "в", transcr = "[ɪn]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "Mein Name ist...", transl = "Меня зовут...", transcr = "[maɪ̯n ˈnaːmə ɪst]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "Ich heiße...", transl = "Меня зовут...", transcr = "[ɪç ˈhaɪ̯sə]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "Ich bin...", transl = "Я...", transcr = "[ɪç bɪn]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "Ich komme aus...", transl = "Я приехал из...", transcr = "[ɪç ˈkɔmə aʊ̯s]", is_important = true, is_learned = false),
        WordEntity(catId = 2, name = "Ich wohne in...", transl = "Я живу в...", transcr = "[ɪç ˈvoːnə ɪn]", is_important = true, is_learned = false),
    )
}