package com.example.langapp.constants

import com.example.langapp.data.entities.WordEntity

object Pronomen {
    val wordList = listOf(
        WordEntity(catId = 4, name = "ich", transl = "я", transcr = "[ɪç]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "du", transl = "ты", transcr = "[duː]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "er", transl = "он", transcr = "[eːɐ̯]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "sie", transl = "она", transcr = "[ziː]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "es", transl = "оно", transcr = "[ɛs]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "wir", transl = "мы", transcr = "[viːɐ̯]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "ihr", transl = "вы", transcr = "[iːɐ̯]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "sie", transl = "они", transcr = "[ziː]", is_important = true, is_learned = false),
        WordEntity(catId = 4, name = "Sie", transl = "Вы", transcr = "[ziː]", is_important = true, is_learned = false),
    )
}