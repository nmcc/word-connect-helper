package com.nmcc.wordconnect.webapi.engine.reader

import org.junit.Assert.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


class FlatFileWordReaderTests : Spek({
    describe("Flat File Reader") {

        describe("reading a file") {
            //val fileUrl = FlatFileWordReaderTests::class.java.getResource("SimpleWordFile.txt")
            val reader = FlatFileWordReader("src/test/resources/SimpleWordFile.txt")
            val content = reader.readWords().toList()

            it("reads all words") {
                assertEquals(9, content.count())
            }

            it("contains fox") {
                assertTrue(content.contains("fox"))
            }

            it("does not contains kotlin") {
                assertFalse(content.contains("kotlin"))
            }
        }
    }
})

