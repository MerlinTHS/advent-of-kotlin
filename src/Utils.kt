import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Returns all values that match the given pattern as a list.
 */
fun String.collect(pattern: Regex): List<String> =
    pattern.findAll(this).map { it.value }.toList()

/**
 * Returns the first value that matches the given pattern or null.
 */
fun String.find(pattern: Regex): String? =
    collect(pattern).firstOrNull()