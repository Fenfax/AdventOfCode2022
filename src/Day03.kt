fun main() {

    val alphabet = ('a'..'z').joinToString("").plus(('A'..'Z').joinToString(""))

    fun postionInAlphabet(char: Char): Int {
        return alphabet.indexOfFirst { it == char } + 1
    }


    fun part1(input: List<String>): Int {
        return input.map { it.chunked(it.length / 2) }
            .map { element -> element[0].first { element[1].contains(it) } }
            .sumOf { postionInAlphabet(it) }

    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.toMutableList() }
            .windowed(3, 3)
            .map { groups -> groups.reduce { acc, chars -> acc.apply { retainAll(chars) } }.first() }
            .sumOf {postionInAlphabet(it)}
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
