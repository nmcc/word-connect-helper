package com.nmcc.wordconnect.webapi

import com.nmcc.wordconnect.webapi.engine.LanguageFileMapping
import com.nmcc.wordconnect.webapi.engine.WordFinderForLanguage
import com.nmcc.wordconnect.webapi.engine.reader.FlatFileWordReader
import com.nmcc.wordconnect.webapi.engine.reader.IWordReader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.FileNotFoundException

@SpringBootApplication
class WebapiApplication {

    @Bean
    fun wordFinderForLanguage(): WordFinderForLanguage {
        val dictionaries = sequence {
            yield(LanguageFileMapping("pt-PT", "words.pt-pt.txt"))
            yield(LanguageFileMapping("en-US", "words.en.txt"))
        }

        return WordFinderForLanguage(dictionaries)
    }
}

fun main(args: Array<String>) {
    runApplication<WebapiApplication>(*args)
}
