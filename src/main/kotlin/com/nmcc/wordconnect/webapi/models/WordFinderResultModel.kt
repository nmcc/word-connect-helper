package com.nmcc.wordconnect.webapi.models

data class WordFinderResultModel(val words: Iterable<String>,
                                 val searchWords: String,
                                 val lengthFrom: Int,
                                 val lengthTo: Int)