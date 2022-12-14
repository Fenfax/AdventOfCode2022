fun main() {

    fun parseSingleSignal(s: String): Signal {
        var signal = Signal(mutableListOf())

        if (!s.contains("]") && !s.contains("[") && !s.contains(",")) {
            return Signal(mutableListOf(), s.takeIf { it != "" }?.toInt() ?: 0)
        }

        var currentString = s.substring(
                s.indexOfFirst { it == '[' } + 1, s.indexOfLast { it == ']' }
        )

        //Problem : 1,[2,[3,[4,[5,6,7]]]],8,9  => maybe: (?<=^|\])[^[]

        signal.multiple.addAll(currentString.split(Regex(",+(?![^\\[]*\\])")).map { parseSingleSignal(it) })

        return signal
    }

    fun part1(input: List<String>): Int {
        val signalList: List<Pair<Signal, Signal>> = input.splitWhen { it == "" }.map { Pair(parseSingleSignal(it[0]), parseSingleSignal(it[1])) }

        return input.size
    }

    val input = readInput("Day13_test")
    println(part1(input))
}


data class Signal(val multiple: MutableList<Signal>, var single: Int? = null)