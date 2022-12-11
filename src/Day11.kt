import java.util.function.Function

fun main() {
    fun firstNumberFromString(s: String): Long? {
        return s.dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }.toLongOrNull()
    }

    fun parseMonkeys(input: List<String>): List<Monkey> {
        val monkeys = input.splitWhen { it == "" }

        val monkeyMap = monkeys.map {
            val number = firstNumberFromString(it[2])
            firstNumberFromString(it[0]) to
                    Monkey(
                        firstNumberFromString(it[0]) ?: 0,
                        ArrayDeque(it[1].split(":")[1].split(",").map { s -> s.trim().toLong() }),
                        when {
                            it[2].contains("+") -> { i: Long -> i + (number!!) }
                            it[2].contains("*") -> { i: Long -> i * (number ?: i) }
                            else -> throw NotImplementedError()
                        },
                        firstNumberFromString(it[3]) ?: 0
                    )
        }

        monkeys.forEach {
            val monkey = monkeyMap[firstNumberFromString(it[0])?.toInt()!!]
            monkey.second.successMonkey = monkeyMap[firstNumberFromString(it[4])?.toInt()!!].second
            monkey.second.failMonkey = monkeyMap[firstNumberFromString(it[5])?.toInt()!!].second
        }

        return monkeyMap.map { it.second }
    }

    fun solve(monkeys: List<Monkey>, rounds: Int, divide: Long): Long {

        val commonMultiple = monkeys.map { it.checkNumber }.reduce{ acc, l -> acc * l }

        for (unused in (1..rounds)) {
            monkeys.forEach {
                it.checkItems(divide, commonMultiple)
            }
        }

        return monkeys.asSequence()
            .sortedByDescending { it.checkedItems }
            .take(2)
            .map { it.checkedItems }
            .reduce { acc, i -> acc * i }
    }

    val input = readInput("Day11")
    println(solve(parseMonkeys(input), 20, 3))
    println(solve(parseMonkeys(input), 10_000,1))
}

data class Monkey(
    val number: Long,
    val holdingItems: ArrayDeque<Long>,
    val newFunction: Function<Long, Long>,
    val checkNumber: Long,
    var successMonkey: Monkey? = null,
    var failMonkey: Monkey? = null,
    var checkedItems: Long = 0
) {

    fun checkItems(divide: Long, commonMultiple: Long) {
        while (!holdingItems.isEmpty()) {
            val item = newFunction.apply(holdingItems.removeFirst()) / (divide)
            if (item.mod(checkNumber) == 0L)
                successMonkey?.holdingItems?.add(item.mod(commonMultiple))
            else
                failMonkey?.holdingItems?.add(item.mod(commonMultiple))
            checkedItems += 1
        }
    }

    override fun toString(): String {
        return "Monkey(number=$number, checkedItems =$checkedItems)"
    }
}