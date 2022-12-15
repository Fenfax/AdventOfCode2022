import org.apache.commons.collections4.ListUtils

fun main() {

    fun parseSingleSignal(s: String): Any {
        val signal = mutableListOf<Any>()

        if (!s.contains("]") && !s.contains("[") && !s.contains(",")) {
            return s.takeIf { it != "" }?.toInt() ?: Unit
        }

        val currentString = s.substring(
            s.indexOfFirst { it == '[' } + 1, s.indexOfLast { it == ']' }
        )

        var start = 0
        var openingCount = 0
        var closingCount = 0

        for (x in currentString.indices) {
            if (currentString[x] == ',' && openingCount == closingCount) {
                signal.add(parseSingleSignal(currentString.substring(start, x)))
                start = x.inc()
            }
            if (currentString[x] == '[') {
                openingCount += 1
            }
            if (currentString[x] == ']') {
                closingCount += 1
            }
        }
        start = currentString.length
        for (x in currentString.indices.reversed()) {
            if (currentString[x] == ',' && openingCount == closingCount) {
                signal.add(parseSingleSignal(currentString.substring(x + 1, start)))
                break
            }
            if (currentString[x] == '[') {
                openingCount += 1
            }
            if (currentString[x] == ']') {
                closingCount += 1
            }
            if (x == 0) {
                signal.add(parseSingleSignal(currentString))
            }
        }

        return signal
    }

    fun compareSignal(signalPair: Pair<Any, Any>): Boolean? {

        if (signalPair.first is Int && signalPair.second is Int) {
            if (signalPair.first == signalPair.second) {
                return null
            }
            return (signalPair.first as Int) < (signalPair.second as Int)
        }

        if (signalPair.first is List<*> && signalPair.second is List<*>) {
            val signalListOne = (signalPair.first as List<*>)
            val signalListTwo = (signalPair.second as List<*>)

            val test = (signalListOne.indices)
                .asSequence()
                .map {
                    if (!signalListTwo.indices.contains(it)) {
                        false
                    } else {
                        compareSignal(Pair(signalListOne[it]!!, signalListTwo[it]!!))
                    }
                }
                .takeWhileInclusive { it == null }
                .toList()

            return test.firstNotNullOfOrNull { it } ?: if (signalListOne.size >= signalListTwo.size) return null else return true

        }

        if (signalPair.second !is List<*>) {
            return compareSignal(Pair(signalPair.first, listOf(signalPair.second as Int)))
        }

        return compareSignal(Pair(listOf(signalPair.first as Int), signalPair.second))

    }

    fun part1(input: List<String>): Int {
        val signalList: List<Pair<Any, Any>> = input.splitWhen { it == "" }.map { Pair(parseSingleSignal(it[0]), parseSingleSignal(it[1])) }

        val signalValidation = signalList.map {
            compareSignal(it)
        }
        return signalValidation.foldIndexed(0) { index, acc, b -> if (b != false) acc + index + 1 else acc }
    }

    fun part2(input: List<String>): Int {

        val dividerPackages = listOf("[[2]]", "[[6]]").map { parseSingleSignal(it) }

        val signalList = input.filter { it != "" }.map { parseSingleSignal(it) }

        val sortedList: List<Any> = ListUtils.union(signalList, dividerPackages)
            .sortedWith { o1: Any, o2: Any -> if (compareSignal(Pair(o1, o2))!!) -1 else 1 }

        return sortedList.foldIndexed(1) { index, acc, signal -> if (dividerPackages.contains(signal)) acc * (index + 1) else acc }
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

fun <T> Sequence<T>.takeWhileInclusive(predicate: (T) -> Boolean) = sequence {
    with(iterator()) {
        while (hasNext()) {
            val next = next()
            yield(next)
            if (!predicate(next)) break
        }
    }
}