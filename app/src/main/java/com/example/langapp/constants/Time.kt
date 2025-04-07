package com.example.langapp.constants

import com.example.langapp.data.entities.WordEntity

object Time {
    val wordList = listOf(
        WordEntity(catId = 7, name = "die Stunde", transl = "час", transcr = "[ˈʃtʊndə]", is_important = false, is_learned = false),
        WordEntity(catId = 7, name = "die Minute", transl = "минута", transcr = "[miˈnuːtə]", is_important = false, is_learned = false),
        WordEntity(catId = 7, name = "die Sekunde", transl = "секунда", transcr = "[zeˈkʊndə]", is_important = false, is_learned = false),
        WordEntity(catId = 7, name = "die Uhr", transl = "часы", transcr = "[uːɐ̯]", is_important = false, is_learned = false),
        WordEntity(catId = 7, name = "die Uhrzeit", transl = "время (на часах)", transcr = "[ˈuːɐ̯ˌtsaɪ̯t]", is_important = false, is_learned = false),
        WordEntity(catId = 7, name = "die Zeit", transl = "время", transcr = "[tsaɪ̯t]", is_important = false, is_learned = false)
    )
}