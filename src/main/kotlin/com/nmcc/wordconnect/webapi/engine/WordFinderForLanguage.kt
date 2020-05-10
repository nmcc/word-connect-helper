package com.nmcc.wordconnect.webapi.engine

import com.nmcc.wordconnect.webapi.engine.reader.FlatFileWordReader
import java.io.FileNotFoundException

data class LanguageFileMapping(val langCode: String,
                               val pathToDictionary: String)

class WordFinderForLanguage(languages: Sequence<LanguageFileMapping>) {
    private val wordFinderByLang = mutableMapOf<String, WordFinder>()

    init {
        languages.forEach {
            val dictionary = this.javaClass.classLoader.getResourceAsStream(it.pathToDictionary)
                    ?: throw FileNotFoundException("Unable to find dictionary ${it.pathToDictionary} for language ${it.langCode}")

            wordFinderByLang[it.langCode.toLowerCase()] = WordFinder(FlatFileWordReader(dictionary))
        }
    }

    fun getWordFinderForLang(langCode: String) = wordFinderByLang[langCode.toLowerCase()]
}