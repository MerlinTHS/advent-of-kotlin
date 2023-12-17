import kotlin.time.measureTime

fun main() {
    measureTime { partOne() }.println()
}

private fun partOne() {
    val lines = readLines("Day12").map(::Line)
    val arrangements = lines.sumOf { it.arrangements }

    println("$arrangements possible arrangements.")
}

data class Line(
    val actual: String,
    val damaged: List<Int>
)

fun String.mutations(index: Int): List<String> {
    if (index > lastIndex) return emptyList()

    if (get(index) != '?')
        return mutations(index +1)

    val operating = replaceRange(index, index+1, ".")
    val damaged = replaceRange(index, index+1, "#")

    return buildList {
        add(operating)
        add(damaged)

        if (substring(index+1).contains('?')) {
            addAll(operating.mutations(index + 1))
            addAll(damaged.mutations(index+1))
        }
    }
}

val Line.arrangements: Int get() =
    actual.mutations(0)
        .filterNot { it.contains("?") }
        .count { it.damaged == damaged }

val String.damaged: List<Int> get() =
    collect("#+".toRegex()).map { it.length }

fun Line(line: String): Line {
    val (actual, damaged) = line.split(" ")
    return Line(actual, damaged.numbers)
}

private val String.numbers: List<Int> get() =
    collect(Regex("\\d+")).map { it.toInt() }
