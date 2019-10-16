package com.nmcc.wordconnect.webapi.engine

import com.nmcc.wordconnect.webapi.engine.reader.IWordReader

class WordFinder(wordReader: IWordReader) {
    private val wordsByFirstLetter: MutableMap<Char, MutableSet<String>> = mutableMapOf()

    init {
        val wordsByLetterIndex = wordReader.readWords()
                .filter { minLength(it, 3) }
                .map { normalize(it) }

        // Index words by every letter
        wordsByLetterIndex.forEach { word ->
            word.forEach { letter ->
                if (!this.wordsByFirstLetter.containsKey(letter)) {
                    this.wordsByFirstLetter[letter] = mutableSetOf()
                }

                this.wordsByFirstLetter[letter]!!.add(word)
            }
        }
    }

    fun findWords(searchLetters: String, length: Int): Iterable<String> {
        val normalizedSearchLetters = normalize(searchLetters)
        val regex = Regex("^[$normalizedSearchLetters]{$length}")

        var result = emptySet<String>()
        normalizedSearchLetters.forEach { letter ->
            val words = wordsByFirstLetter[letter]
                    ?.filter { word -> regex.matches(word) }
                    ?.filter { word -> word.containsAllLetters(normalizedSearchLetters) }
                    ?: emptySet<String>()

            result = result.union(words)
        }

        return result
    }

    private fun String.containsAllLetters(searchLetters: String): Boolean {
        // TODO: find a more efficient way to build a mutable list of chars
        val letterList = searchLetters.toCharArray().toMutableList()

        this.forEach { letter ->
            if (!letterList.contains(letter))
                return false

            letterList.remove(letter)
        }

        // word contains all letters within search letters
        return true
    }

    private fun normalize(word: String): String = word.trim().toUpperCase()

    private fun minLength(word: String, minLength: Int): Boolean = word.length >= minLength
}