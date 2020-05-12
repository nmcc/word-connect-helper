package com.nmcc.wordconnect.webapi.engine

import com.nmcc.wordconnect.webapi.engine.reader.IWordReader

class WordFinder(wordReader: IWordReader) {
    private val charArrayLength = 'Z' - 'A' + 1
    private val words = Array(charArrayLength) { mutableListOf<String>() }

    private val filterRegex = Regex("[A-Za-z][a-z]+")

    init {
        wordReader.use {
            wordReader.readWords()
                    .filter { it.hasMinLength(3) }
                    .filter { filterRegex.matches(it) }
                    .map { normalize(it) }
                    .forEach { word ->
                        word.forEach { letter ->
                            words[indexOfLetter(letter)].add(word)
                        }
                    }
        }
    }

    private fun indexOfLetter(c: Char) = c - 'A'

    fun findWords(searchLetters: String, length: Int): Iterable<String> {
        val normalizedSearchLetters = normalize(searchLetters)

        // Pre-match every word with the required length
        var preMatches = emptySet<String>()
        normalizedSearchLetters.forEach { letter ->
            val wordsForLetter = words[indexOfLetter(letter)]
                    .filter { it.hasLength(length) }

            preMatches = preMatches.union(wordsForLetter)
        }

        val regex = Regex("^[$normalizedSearchLetters]{$length}$")
        return preMatches
                .filter { regex.matches(it) }
                .filter { it.containsAllLetters(normalizedSearchLetters) }
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

    private fun normalize(s: String) = s.trim().toUpperCase()

    private fun String.hasMinLength(minLength: Int) = this.length >= minLength

    private fun String.hasLength(minLength: Int) = this.length == minLength
}