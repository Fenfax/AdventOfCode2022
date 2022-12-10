fun main() {
    fun part1(input: List<ValueCommand>): Int {
        var register = 1
        var cycleCount = 0
        var score = 0

        for (valueCommand in input) {
            score += (cycleCount + 1..cycleCount + valueCommand.command.cycleCount)
                .firstOrNull { (it - 20).mod(40) == 0 }?.let { it * register } ?: 0
            cycleCount += valueCommand.command.cycleCount
            register += valueCommand.value
        }
        return score
    }

    fun part2(input: List<ValueCommand>) {
        var register = 1
        var cycleCount = 1
        val lines = mutableListOf<List<Char>>()
        var displayLine = mutableListOf<Char>()

        for (valueCommand in input) {
            val spite = CharArray(40) { if (it in register - 1..register + 1) '#' else '.' }
            repeat(valueCommand.command.cycleCount) {
                displayLine.add(spite[displayLine.size])
                if ((it + cycleCount).mod(40) == 0) {
                    lines.add(displayLine)
                    displayLine = mutableListOf()
                }
            }
            cycleCount += valueCommand.command.cycleCount
            register += valueCommand.value
        }

        lines.forEach { println(it.joinToString("")) }
    }

    val input = readInput("Day10").map { cmd -> cmd.split(" ").let { ValueCommand(Command.valueOf(it[0].uppercase()), it.getOrNull(1)?.toInt() ?: 0) } }
    println(part1(input))
    part2(input)
}

enum class Command(val cycleCount: Int) {
    ADDX(2),
    NOOP(1)
}

data class ValueCommand(val command: Command, val value: Int)
