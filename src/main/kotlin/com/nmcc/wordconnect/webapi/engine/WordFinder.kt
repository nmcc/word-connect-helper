package com.nmcc.wordconnect.webapi.engine

import com.nmcc.wordconnect.webapi.engine.reader.IWordReader
import org.springframework.stereotype.Service

@Service
class WordFinder(wordReader: IWordReader) {
    private val wordsByFirstLetter: MutableMap<Char, MutableSet<String>> = mutableMapOf()

    private val filterRegex = Regex("[A-Za-z][a-z]+")

    init {
        val wordsByLetterIndex = wordReader.readWords()
                .filter { it.hasMinLength(3) }
                .filter { filterRegex.matches(it) }
                .map { it.normalize() }

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
        val normalizedSearchLetters = searchLetters.normalize()

        // Pre-match every word with the required length
        var preMatches = emptySet<String>()
        normalizedSearchLetters.forEach { letter ->
            val wordsForLetter = wordsByFirstLetter[letter]
                    ?.filter { it.hasLength(length) }
                    ?: emptySet<String>()

            preMatches = preMatches.let { it.union(wordsForLetter) }
        }

        val regex = Regex("^[$normalizedSearchLetters]{$length}")
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

    private fun String.normalize() = this.trim().toUpperCase()

    private fun String.hasMinLength(minLength: Int) = this.length >= minLength

    private fun String.hasLength(minLength: Int) = this.length == minLength
}