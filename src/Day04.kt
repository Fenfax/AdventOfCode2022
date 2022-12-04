fun main() {
    val parSplit = ','
    val rangeSplit = '-'

    fun getRangeList(input: List<String>) = input
        .map { fullString -> fullString.split(parSplit).map { it.split(rangeSplit) } }
        .map { pairs -> pairs.map { it[0].toInt()..it[1].toInt() } }

    fun part1(input: List<String>): Int {
        val splitInput = getRangeList(input)
            .filter { (it[0].first in it[1] && it[0].last in it[1]) || (it[1].first in it[0] && it[1].last in it[0]) }

        return splitInput.size
    }

    fun part2(input: List<String>): Int {
        val splitInput = getRangeList(input)
            .filter { it[0].first in it[1] || it[0].last in it[1] || it[1].first in it[0] || it[1].last in it[0] }

        return splitInput.size
    }


    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
