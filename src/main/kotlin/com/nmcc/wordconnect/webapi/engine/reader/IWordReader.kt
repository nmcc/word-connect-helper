package com.nmcc.wordconnect.webapi.engine.reader

interface IWordReader {
    fun readWords(): Sequence<String>

}