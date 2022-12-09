import java.awt.Point

fun main() {
    fun part1(input: List<Pair<Direction, Int>>): Int {
        val headPoint = Point()
        val tailPoint = Point()

        val tailPointsVisited = mutableSetOf<Point>()

        for (command in input) {
            for (unused in (1..command.second)) {
                val oldHead: Point = headPoint.clone() as Point
                when (command.first) {
                    Direction.R -> headPoint.translate(1, 0)
                    Direction.L -> headPoint.translate(-1, 0)
                    Direction.U -> headPoint.translate(0, 1)
                    Direction.D -> headPoint.translate(0, -1)
                }
                if(headPoint.distance(tailPoint) > 1.5){
                    tailPointsVisited.add(tailPoint.clone() as Point)
                    tailPoint.location = oldHead
                }
            }
        }
        tailPointsVisited.add(tailPoint.clone() as Point)

        return tailPointsVisited.size
    }


    val input = readInput("Day09").map { cmd -> cmd.split(" ").let { Pair(Direction.valueOf(it[0]), it[1].toInt()) } }

    println(part1(input))
}

private enum class Direction {
    R,
    L,
    U,
    D
}