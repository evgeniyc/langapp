package com.example.langapp.constants

import com.example.langapp.data.entities.Category

object Categories {
    val categoryList = listOf(
        Category(
            id = 1,
            name = "Begrüßung und Verabschiedung",
            transl = "Приветствие и прощание",
            transcr = "[bəˈɡʁyːsʊŋ ʊnt fɛɐ̯ʔapˈʃiːdʊŋ]"
        ),
        Category(id = 2, name = "Sich vorstellen", transl = "Знакомство", transcr = "[zɪç ˈfoːɐ̯ˌʃtɛlən]"),
        Category(id = 3, name = "Familie", transl = "Семья", transcr = "[faˈmiːli̯ə]"),
        Category(id = 4, name = "Berufe", transl = "Профессии", transcr = "[bəˈʁuːfə]"),
        Category(
            id = 5,
            name = "Länder und Sprachen",
            transl = "Страны и языки",
            transcr = "[ˈlɛndɐ ʊnt ˈʃpʁaːxn̩]"
        ),
        Category(id = 6, name = "Zahlen", transl = "Числа", transcr = "[ˈtsaːlən]"),
        Category(id = 7, name = "Zeit", transl = "Время", transcr = "[tsaɪ̯t]"),
        Category(
            id = 8,
            name = "Wochentage, Monate, Jahreszeiten",
            transl = "Дни недели, месяцы, времена года",
            transcr = "[ˈvɔxn̩ˌtaːɡə, ˈmoːnatə, ˈjaːʁəsˌtsaɪ̯tn̩]"
        ),
        Category(id = 9, name = "Farben", transl = "Цвета", transcr = "[ˈfaʁbn̩]"),
        Category(id = 10, name = "Wetter", transl = "Погода", transcr = "[ˈvɛtɐ]"),
        Category(id = 11, name = "Wohnung", transl = "Квартира", transcr = "[ˈvoːnʊŋ]"),
        Category(id = 12, name = "Möbel", transl = "Мебель", transcr = "[ˈmøːbl̩]"),
        Category(id = 13, name = "Essen", transl = "Еда", transcr = "[ˈɛsn̩]"),
        Category(id = 14, name = "Getränke", transl = "Напитки", transcr = "[ɡəˈtʁɛŋkə]"),
        Category(id = 15, name = "Einkaufen", transl = "Покупки", transcr = "[ˈaɪ̯nˌkaʊ̯fn̩]"),
        Category(id = 16, name = "Kleidung", transl = "Одежда", transcr = "[ˈklaɪ̯dʊŋ]"),
        Category(id = 17, name = "Körperteile", transl = "Части тела", transcr = "[ˈkœʁpɐˌtaɪ̯lə]"),
        Category(id = 18, name = "Beim Arzt", transl = "У врача", transcr = "[baɪ̯m ˈaʁt͡st]"),
        Category(id = 19, name = "In der Stadt", transl = "В городе", transcr = "[ɪn deːɐ̯ ʃtat]"),
        Category(id = 20, name = "Verkehrsmittel", transl = "Транспорт", transcr = "[fɛɐ̯ˈkeːɐ̯sˌmɪtl̩]"),
        Category(id = 21, name = "Freizeit", transl = "Отдых", transcr = "[ˈfʁaɪ̯ˌtsaɪ̯t]"),
        Category(id = 22, name = "Hobbys", transl = "Хобби", transcr = "[ˈhɔbiːs]"),
        Category(id = 23, name = "Sport", transl = "Спорт", transcr = "[ʃpɔʁt]"),
        Category(id = 24, name = "Tiere", transl = "Животные", transcr = "[ˈtiːʁə]"),
        Category(id = 25, name = "In der Schule", transl = "В школе", transcr = "[ɪn deːɐ̯ ˈʃuːlə]"),
        Category(id = 26, name = "Im Büro", transl = "В офисе", transcr = "[ɪm byˈʁoː]"),
        Category(id = 27, name = "Verben", transl = "Глаголы", transcr = "[ˈvɛʁbn̩]"),
        Category(id = 28, name = "Adjektive", transl = "Прилагательные", transcr = "[atjɛkˈtiːvə]"),
        Category(id = 29, name = "Präpositionen", transl = "Предлоги", transcr = "[pʁɛpozitsi̯oːnən]"),
        Category(id = 30, name = "Pronomen", transl = "Местоимения", transcr = "[pʁoˈnoːmən]"),
        Category(id = 31, name = "Fragewörter", transl = "Вопросительные слова", transcr = "[ˈfʁaːɡəˌvœʁtɐ]"),
        Category(id = 32, name = "Adverbien", transl = "Наречия", transcr = "[atˈvɛʁbi̯ən]")
    )
}