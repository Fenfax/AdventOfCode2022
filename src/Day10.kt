fun main() {
    fun part1(input: List<ValueCommand>): Int {
        var register = 1
        var cycleCount = 0
        var score = 0

        for (valueCommand in input){
            score += (cycleCount + 1 .. cycleCount+ valueCommand.command.cycleCount)
                .firstOrNull { (it - 20).mod(40) == 0 }?.let { it * register } ?: 0
            cycleCount += valueCommand.command.cycleCount
            register += valueCommand.value
        }
        return score
    }


    val input = readInput("Day10").map { cmd -> cmd.split(" ").let { ValueCommand(Command.valueOf(it[0].uppercase()), it.getOrNull(1)?.toInt() ?: 0) } }
    println(part1(input))
}

enum class Command(val cycleCount: Int){
    ADDX(2),
    NOOP(1)
}

data class ValueCommand(val command: Command, val value: Int)
