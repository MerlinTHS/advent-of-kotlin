import kotlin.math.pow
import kotlin.math.sqrt

const val width = 141
val schematic = readLines("Day03").joinToString(".")

fun main() {
    solvePartOne()
    solvePartTwo()
}

fun solvePartOne() {
    val numbers = findNumbers()
    val symbols = findSymbols(Regex("[^\\d.]"))

    val sum = numbers
        .filterKeys { positions -> positions.any { symbols.asIterable() touch it } }
        .values.sumOf { it.toInt() }

    println("Sum is $sum")
}

fun solvePartTwo() {
    val numbers = findNumbers()
    val symbols = findSymbols(Regex("\\*"))

    val gearRatio = symbols
        .map { symbol -> numbers
            .filterKeys { positions -> positions touch symbol }
            .values.map { it.toInt() }
            .asPair() ?: (0 to 0)
        }.sumOf { it.first * it.second }

    println("Gear ratio is $gearRatio")
}

// The list must be exactly of length 2!
fun <T> List<T>.asPair() =
    takeIf { it.size == 2 }?.run { get(0) to get(1) }

fun findSymbols(regex: Regex): Sequence<Vector> =
    regex.findAll(schematic).map { Vector(it.range.first) }

// Must be the positions first, because each number can appear multiple times.
fun findNumbers(): Map<List<Vector>, String> =
    Regex("\\d+").findAll(schematic).associate { it.range.map(::Vector) to it.value }

infix fun Iterable<Vector>.touch(position: Vector): Boolean =
    any { (it - position).amount <= sqrt(2.0) }

data class Vector(val x: Int, val y: Int) {
    constructor(position: Int) : this(
        x = position / width,
        y = position % width
    )

    operator fun minus(other: Vector) = Vector(
        x = x - other.x,
        y = y - other.y
    )

    val amount = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))
}