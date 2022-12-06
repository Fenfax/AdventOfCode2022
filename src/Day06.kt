fun main() {

    fun findUnique(c: Char, queue: ArrayDeque<Char>, uniqueAmount: Int): Boolean {
        queue.add(c)
        if (queue.size >= uniqueAmount && queue.distinct().size == queue.size) {
            queue.clear()
            return false
        }
        if (queue.size >= uniqueAmount)
            queue.removeFirst()
        return true
    }

    fun solve(input: List<String>, uniqueAmount: Int): Int {

        val queue: ArrayDeque<Char> = ArrayDeque()

        return input.map {
            it.toList()
                .takeWhile { c -> findUnique(c, queue, uniqueAmount) }
                .size + 1
        }.first()

    }

    fun solveShort(input: String, uniqueAmount: Int): Int {
        val work = input.toList()

        return (1..input.length).toList()
            .takeWhile { work.subList(it, it+ uniqueAmount).let {list -> list.size != list.distinct().size } }.last() + uniqueAmount + 1
    }


    val input = readInput("Day06")
    println(solve(input, 4))
    println(solve(input, 14))
    println(solveShort(input[0], 4))
    println(solveShort(input[0], 14))
}
