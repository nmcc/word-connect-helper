package com.nmcc.wordconnect.webapi.engine

import com.nmcc.wordconnect.webapi.engine.reader.IWordReader
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object WordFinderTests : Spek({
    describe("Word store") {
        val wordReaderMock = mock(IWordReader::class.java)
        Mockito.`when`(wordReaderMock.readWords())
                .thenReturn(sequenceOf(
                        "the", "quick", "brown", "fox", "jumps", "over", "a", "lazy", "dog", "pot", "top", "opt"
                ))

        val subject = WordFinder(wordReaderMock)

        it("returns quick") {
            assertTrue(subject.findWords("quick", 5).contains("QUICK"))
        }

        it("doesn't return 'a'") {
            assertFalse(subject.findWords("a", 1).any())
        }

        it("contains all words for o, p and t") {
            val words = subject.findWords("top", 3)
            assertEquals(3, words.count())
            assertTrue(words.contains("TOP"))
            assertTrue(words.contains("POT"))
            assertTrue(words.contains("OPT"))
        }
    }
})
