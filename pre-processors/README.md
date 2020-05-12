# Dictionary preprocessor

The dictionaries' preprocessor prepares the dictionary to be used by the application.

It performs the following actions:
* Filter words with 3 characters minimum length
* Filter nouns - words that start with a capital letter
* Filter words on the blacklist

## Running

```shell
cd pre-processors
kotlinc -script dictionary-preprocessor.kts
```