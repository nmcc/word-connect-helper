package com.nmcc.wordconnect.webapi.engine.reader

import java.io.Closeable
import java.io.InputStream

class FlatFileWordReader(inputStream: InputStream) : IWordReader, Closeable {
    private val reader = inputStream.bufferedReader()

    override fun readWords(): Sequence<String> = reader.lineSequence()

    override fun close() = this.reader.close()
}