package com.nmcc.wordconnect.webapi.engine.reader

import java.io.Closeable
import java.io.File

class FlatFileWordReader(filePath: String) : IWordReader, Closeable {

    private val reader = File(filePath).bufferedReader()

    override fun readWords(): Sequence<String> = reader.lineSequence()

    override fun close() = this.reader.close()
}