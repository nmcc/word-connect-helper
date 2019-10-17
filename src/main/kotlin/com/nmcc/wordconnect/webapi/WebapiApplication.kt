package com.nmcc.wordconnect.webapi

import com.nmcc.wordconnect.webapi.engine.reader.FlatFileWordReader
import com.nmcc.wordconnect.webapi.engine.reader.IWordReader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.FileNotFoundException

@SpringBootApplication
class WebapiApplication {

	@Bean
	fun wordReader(): IWordReader {
		val englishDictionary = this.javaClass.classLoader.getResourceAsStream("words-en.txt")
				?: throw FileNotFoundException("Unable to find dictionary")

		return FlatFileWordReader(englishDictionary)
	}
}

fun main(args: Array<String>) {
	runApplication<WebapiApplication>(*args)
}
