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
            WordFinderResultModel(wordFinder.findWords(letters, length), letters, length)
}