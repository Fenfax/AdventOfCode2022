import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/inputs", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun List<String>.splitWhen(predicate: (String)-> Boolean):List<List<String>> =
    foldIndexed(mutableListOf<MutableList<String>>()) { index, list, string ->
        when {
            predicate(string) -> if (index < size - 1 && !predicate(get(index + 1))) list.add(mutableListOf()) // Adds  a new List within the output List; To prevent continuous delimiters -- !predicate(get(index+1))
            list.isNotEmpty() -> list.last().add(string) // Just adding it to lastly added sub-list, as the string is not a delimiter
            else -> list.add(mutableListOf(string)) // Happens for the first String
        }
        list
    }

