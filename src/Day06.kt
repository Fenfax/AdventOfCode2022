fun main() {

    fun findUnique(c: Char, queue: ArrayDeque<Char>, uniqueAmount: Int):Boolean{
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
                .takeWhile { c -> findUnique(c,queue, uniqueAmount) }
                .size + 1
        }.first()

    }


    val input = readInput("Day06")
    println(solve(input,4))
    println(solve(input,14))
}
