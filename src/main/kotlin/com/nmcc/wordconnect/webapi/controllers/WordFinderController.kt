package com.nmcc.wordconnect.webapi.controllers

import com.nmcc.wordconnect.webapi.engine.WordFinderForLanguage
import com.nmcc.wordconnect.webapi.models.WordFinderResultModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class WordFinderController(private val wordFinderForLanguage: WordFinderForLanguage) {

    @GetMapping("/wordconnect/find")
    @ResponseBody
    fun findWords(@RequestParam letters: String, @RequestParam length: Int, @RequestParam lang: String?, response: HttpServletResponse)
            : WordFinderResultModel? {
        val wordFinder = getWordFinder(lang)

        if (wordFinder == null) {
            response.sendError(400, "Unsupported language $lang")
            return null
        }

        return WordFinderResultModel(wordFinder.findWords(letters, length), letters, length, length)
    }

    @GetMapping("/wordconnect/find/multiple")
    @ResponseBody
    fun findWordsMultiLength(@RequestParam letters: String, @RequestParam lengthFrom: Int, @RequestParam lengthTo: Int,
                             @RequestParam lang: String?, response: HttpServletResponse)
            : WordFinderResultModel? {
        val wordFinder = getWordFinder(lang)

        if (wordFinder == null) {
            response.sendError(400, "Unsupported language $lang")
            return null
        }

        val words = mutableListOf<String>()

        (lengthFrom..lengthTo).forEach { length ->
            words.addAll(wordFinder.findWords(letters, length))
        }

        return WordFinderResultModel(words, letters, lengthFrom, lengthTo)
    }

    private fun getWordFinder(langCode: String?) = wordFinderForLanguage.getWordFinderForLang(langCode ?: "en-US")
}