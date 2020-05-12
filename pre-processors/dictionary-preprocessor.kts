import java.io.File

val wordMatchRegex = Regex("^[a-z]{2,}$")

val charMap = mutableMapOf(
        'ã' to 'a', 'á' to 'a', 'à' to 'a', 'â' to 'a',
        'é' to 'e', 'è' to 'e', 'ê' to 'e',
        'í' to 'i', 'ì' to 'i', 'î' to 'i',
        'õ' to 'o', 'ó' to 'o', 'ò' to 'o', 'ô' to 'o',
        'ú' to 'u', 'ù' to 'u',
        'ç' to 'c')

// Add upper case letters
charMap
        .map { (k, v) -> Pair(k.toString().toUpperCase()[0], v.toString().toUpperCase()[0]) }
        .forEach { charMap.put(it.first, it.second) }

val lettersToReplace = charMap.keys

fun replaceChars(word: String): String {
    var newWord = word

    newWord.forEach {
        if (lettersToReplace.contains(it)) {
            newWord.replace(it, charMap[it]!!)
        }
    }

    return newWord
}

fun processDictionary(pathToDictionary: String, pathToBlacklist: String, pathToOutput: String) {
    val blackList = File(pathToBlacklist).bufferedReader().readLines()

    val outFilename = File(pathToOutput)
    outFilename.delete()

    val out = outFilename.outputStream().bufferedWriter()
    out.use {
        File(pathToDictionary).forEachLine { word ->
            if (wordMatchRegex.matches(word) && !blackList.contains(word)) {
                val newWord = replaceChars(word)
                out.write(newWord)
                out.write("\r\n")
            }
        }
    }
}

processDictionary(
        "pt-pt/wordlist-big-latest.txt",
        "pt-pt/words.pt-pt.blacklist.txt",
        "../src/main/resources/words.pt-pt.txt")

processDictionary(
        "en-US/words.en.txt",
        "en-US/words.en.blacklist.txt",
        "../src/main/resources/words.en.txt")