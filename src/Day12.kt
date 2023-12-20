import kotlin.time.measureTime

fun main() {
    listOf(::partOne, ::partTwo).forEach {
        measureTime(it).println()
    }
}

private fun partOne() {
    val arrangements = readLines("Day12").sumOf { line ->
        val (pattern, solution) = line.split(" ")
        pattern.arrangements(solution.numbers)
    }

    println("$arrangements possible arrangements.")
}

operator fun <T> List<T>.times(number: Int): List<T> =
    List(number) { this }.flatten()

private fun partTwo() {
    val arrangements = readLines("Day12").sumOf { line ->
        val (pattern, solution) = line.split(" ")

        (listOf(pattern) * 5).joinToString("?")
            .arrangements(solution.numbers * 5)
    }

    println("$arrangements possible arrangements.")
}

fun Boolean.toLong(): Long =
    if (this) 1L else 0L

val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

fun String.arrangements(solution: List<Int>): Long =
    cache.getOrPut(this to solution) { sumArrangements(solution) }

private inline fun String.sumArrangements(solution: List<Int>): Long {
    ifEmpty { return solution.isEmpty().toLong() }
    if (solution.isEmpty()) return none { it == '#' }.toLong()

    var total = 0L
    if (first() != '#')
        total += drop(1).arrangements(solution)

    if (takeWhile { it != '.' }.length >= solution[0] && getOrNull(solution[0]) != '#')
        total += drop(solution[0]+1).arrangements(solution.drop(1))

    return total
}

private val String.numbers: List<Int> get() =
    collect(Regex("\\d+")).map { it.toInt() }