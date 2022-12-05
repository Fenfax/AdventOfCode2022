fun main() {
    fun part1(input: Map<Int, MutableList<Char>>, commands: List<List<Int>>): String {

        for (number in commands) {
            input[number[1]]?.let {
                input[number[2]]?.addAll((1..number[0]).map { _ -> it.removeLast() })
            }
        }

        return input.map { it.value.last() }.joinToString("")
    }

    fun generateStorage(input: List<String>): Map<Int, MutableList<Char>> {
        val positions = input.last().toList().asSequence().mapIndexedNotNull { index, c -> index.takeIf { c.isDigit() } }.toList()

        return positions.mapIndexed { index, pos ->
            index + 1 to input.filterIndexed { ind, _ -> ind != input.size - 1 }.reversed().mapNotNull { it[pos].takeIf { char -> char.isLetter() } }
                .toMutableList()
        }.toMap()
    }

    val input = readInput("Day05")
    val splitInput = input.splitWhen { it == "" }
    val groups = generateStorage(splitInput[0])
    val commands = splitInput[1].map { it.split(" ").filter { s -> s.all { c -> c.isDigit() } }.map { s -> s.toInt() } }
    println(part1(groups, commands))
}
