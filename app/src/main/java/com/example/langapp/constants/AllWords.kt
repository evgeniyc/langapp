package com.example.langapp.constants

import com.example.langapp.data.entities.WordEntity

object AllWords {
    val allWords: List<WordEntity> =
                Greetings.wordList +
                Numbers.wordList +
                Time.wordList +
                Family.wordList +
                Colors.wordList +
                Pronouns.wordList
}