package com.nmcc.wordconnect.webapi.engine.reader

import java.io.Closeable

interface IWordReader : Closeable {
    fun readWords(): Sequence<String>

}