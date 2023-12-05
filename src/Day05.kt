private val almanac = readLines("Day05").joinToString("\n")

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val location = almanac.seeds.minOf { locationOf(it) }
    println("Nearest location is $location")
}

private fun partTwo() {
    val location = almanac.seeds.asRanges()
        .minOf { seeds -> seeds.minOf { locationOf(it) } }

    println("Nearest location is $location")
}

fun locationOf(seed: Long): Long =
    almanac.seedMappings.fold(seed) { source, mappings ->
        mappings.firstNotNullOfOrNull { it[source] } ?: source
    }

private data class RangeMapping(val source: Long, val target: Long, val length: Long) {
    operator fun contains(value: Long): Boolean =
        value in source..(source + length)

    operator fun get(value: Long): Long? =
        (target + value - source).takeIf { value in this }
}

private val String.seedMappings get() =
    blocks.map { it.numbers.asRangeMappings() }

private fun List<Long>.asRangeMappings(): List<RangeMapping> =
    chunked(3) { RangeMapping(target = it[0], source = it[1], length = it[2]) }

private fun List<Long>.asRanges(): List<LongRange> =
    chunked(2) { it[0]..it[0]+it[1] }

private val String.seeds: List<Long> get() =
    find(Regex("seeds: (\\d+\\s+)+"))?.numbers.orEmpty()

private val String.blocks: List<String> get() =
    collect(Regex("(?<=map:)[\\d\\s]+"))

private val String.numbers: List<Long> get() =
    collect(Regex("\\d+")).map { it.toLong() }