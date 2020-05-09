package com.nmcc.wordconnect.webapi.controllers

import com.nmcc.wordconnect.webapi.engine.WordFinder
import com.nmcc.wordconnect.webapi.models.WordFinderResultModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WordFinderController(val wordFinder: WordFinder) {

    @GetMapping("/wordconnect/find")
    @ResponseBody
    fun findWords(@RequestParam letters: String, @RequestParam length: Int) =
            WordFinderResultModel(wordFinder.findWords(letters, length), letters, length, length)

    @GetMapping("/wordconnect/find/multiple")
    @ResponseBody
    fun findWordsMultiLength(@RequestParam letters: String, @RequestParam lengthFrom: Int, @RequestParam lengthTo: Int): WordFinderResultModel {
        val words = mutableListOf<String>()

        (lengthFrom..lengthTo).forEach { length ->
            words.addAll(wordFinder.findWords(letters, length))
        }

        return WordFinderResultModel(words, letters, lengthFrom, lengthTo)
    }
}