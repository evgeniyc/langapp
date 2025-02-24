package com.example.langapp.constants

import com.example.langapp.data.entities.Word

object Words {
    val wordList = listOf(
        Word(catId = 1, name = "Hallo", transl = "Привет", transcr = "[haˈloː]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Guten Tag", transl = "Добрый день", transcr = "[ˈɡuːtn̩ ˈtaːk]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Guten Morgen", transl = "Доброе утро", transcr = "[ˈɡuːtn̩ ˈmɔʁɡn̩]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Guten Abend", transl = "Добрый вечер", transcr = "[ˈɡuːtn̩ ˈaːbn̩t]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Gute Nacht", transl = "Спокойной ночи", transcr = "[ˈɡuːtə ˈnaxt]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Tschüss", transl = "Пока", transcr = "[t͡ʃʏs]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Auf Wiedersehen", transl = "До свидания", transcr = "[aʊ̯f ˈviːdɐˌzeːən]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Bis bald", transl = "До скорой встречи", transcr = "[bɪs ˈbalt]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Bis später", transl = "До встречи позже", transcr = "[bɪs ˈʃpɛːtɐ]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Servus", transl = "Привет/Пока", transcr = "[ˈzɛʁvʊs]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Grüß Gott", transl = "Здравствуйте", transcr = "[ɡʁyːs ˈɡɔt]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Grüß dich", transl = "Здравствуй", transcr = "[ɡʁyːs dɪç]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Willkommen", transl = "Добро пожаловать", transcr = "[vɪlˈkɔmən]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Leb wohl", transl = "Прощай", transcr = "[leːp voːl]", is_important = false, is_learned = false),
        Word(catId = 1, name = "Mach's gut", transl = "Всего хорошего", transcr = "[maxs ɡuːt]", is_important = false, is_learned = false)
    )
}
