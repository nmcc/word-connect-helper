package com.nmcc.wordconnect.webapi.engine.reader

import org.junit.Assert.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


class FlatFileWordReaderTests : Spek({
    describe("Flat File Reader") {

        describe("reading a file") {
            val values = "the\n" +
                    "quick\n" +
                    "brown\n" +
                    "fox\n" +
                    "jumps\n" +
                    "over\n" +
                    "the\n" +
                    "lazy\n" +
                    "dog"
            val reader = FlatFileWordReader(values.byteInputStream())
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

