import kotlin.math.pow

val scratchcards = readLines("Day04")

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val totalPoints = scratchcards.sumOf { it.correctNumbers.points }
    println("Total points: $totalPoints")
}

private fun partTwo() {
    val correctNumbers = scratchcards.map { it.correctNumbers }
    val copies = MutableList(correctNumbers.size) { 1 }

    for (card in copies.indices) {
        repeat (copies[card]) {
            repeat (correctNumbers[card]) {
                if (card+it+1 <= copies.lastIndex) copies[card+it+1]++
            }
        }
    }

    println("Total cards: ${copies.sum()}")
}

private val String.correctNumbers: Int get() =
    givenNumbers.intersect(winningNumbers).size

private val Int.points: Int get() =
    (2.0.pow(this) / 2).toInt()

private val String.givenNumbers get() =
    Regex("(?<=\\|).*\$").find(this)?.numbers.orEmpty()

private val String.winningNumbers get() =
    Regex("(?<=:)(.*)(?=\\|)").find(this)?.numbers.orEmpty()

private val MatchResult.numbers: Set<Int> get() =
    Regex("\\d+").findAll(value).map { it.value.toInt() }.toSet()
