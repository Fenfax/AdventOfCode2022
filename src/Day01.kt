fun main() {
    fun part1(input: List<String>): Int {
        val splitInput = input.splitWhen { it == "" }

        return splitInput.maxOf{ mapElement -> mapElement.sumOf { it.toInt() } }
    }

    fun part2(input: List<String>): Int {
        val splitInput = input.splitWhen { it == "" }

        return splitInput.map { mapElement -> mapElement.sumOf { it.toInt() } }.sortedByDescending { it }.take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
