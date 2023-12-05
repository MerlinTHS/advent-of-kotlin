val almanac = readLines("Day05").joinToString("\n")

fun main() = with(almanac) {
    val location = seeds.minOf { seed ->
        seedMappings.fold(seed) { source, mappings ->
            mappings.firstNotNullOfOrNull { it[source] } ?: source
        }
    }

    println("Nearest location is $location")
}

data class RangeMapping(val source: Long, val target: Long, val length: Long) {
    operator fun contains(value: Long): Boolean =
        value in source..(source + length)

    operator fun get(value: Long): Long? =
        (target + value - source).takeIf { value in this }
}

val String.seedMappings get() =
    blocks.map { it.numbers.toRangeMappings() }

fun List<Long>.toRangeMappings(): List<RangeMapping> = buildList {
    for (i in this@toRangeMappings.indices step 3) {
        add(RangeMapping(
            target = this@toRangeMappings[i],
            source = this@toRangeMappings[i+1],
            length = this@toRangeMappings[i+2]
        ))
    }
}

val String.seeds: List<Long> get() =
    find(Regex("seeds: (\\d+\\s+)+"))?.numbers.orEmpty()

val String.blocks: List<String> get() =
    collect(Regex("(?<=map:)[\\d\\s]+"))

val String.numbers: List<Long> get() =
    collect(Regex("\\d+")).map { it.toLong() }