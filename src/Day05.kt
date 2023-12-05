val demo = readLines("Day05_Demo").joinToString("\n")

fun main() = with(demo) {
    val location = seeds.minOf { seed ->
        seedMappings.fold(seed) { current, mappings -> mappings[current] ?: current }
    }

    println("Nearest location is $location")
}

data class Mapping(val source: Long, val target: Long, val length: Int)

val String.seedMappings: List<Map<Long, Long>>
    get() = blocks
        .map { it.numbers.toRangesMappings() }
        .map { normalize(it) }

fun List<Long>.toRangesMappings(): List<Mapping> = buildList {
    for (i in this@toRangesMappings.indices step 3) {
        add(Mapping(
            target = this@toRangesMappings[i],
            source = this@toRangesMappings[i+1],
            length = this@toRangesMappings[i+2].toInt()
        ))
    }
}

/**
 * Converts the range-based mapping into a simple map.
 */
private fun normalize(mappings: List<Mapping>): Map<Long, Long> =
    mappings.flatMap { List(it.length) { i -> (it.source + i) to (it.target + i) } }.toMap()

private val String.seeds: List<Long> get() =
    find(Regex("seeds: (\\d+\\s+)+"))?.numbers.orEmpty()

private val String.blocks: List<String> get() =
    collect(Regex("(?<=map:)[\\d\\s]+"))

private val String.numbers: List<Long> get() =
    collect(Regex("\\d+")).map { it.toLong() }