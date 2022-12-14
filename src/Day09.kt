import java.awt.Point
import kotlin.math.sign

fun main() {
    fun solve(input: List<Pair<Direction, Int>>, pointCount: Int): Int {
        val points = (0 until pointCount).map { Point() }

        val tailPointsVisited = mutableSetOf(Point())

        for (command in input) {
            for (unused in (1..command.second)) {
                when (command.first) {
                    Direction.R -> points[0].translate(1, 0)
                    Direction.L -> points[0].translate(-1, 0)
                    Direction.U -> points[0].translate(0, 1)
                    Direction.D -> points[0].translate(0, -1)
                }
                for (i in (1 until pointCount)) {
                    if (points[i - 1].distance(points[i]) > 1.5) {
                        val translateX = points[i - 1].x - points[i].x
                        val translateY = points[i - 1].y - points[i].y

                        points[i].translate(translateX.sign, translateY.sign)
                        if (i == pointCount - 1) {
                            tailPointsVisited.add(Point(points[i]))
                        }
                    }
                }
            }
        }

        return tailPointsVisited.size
    }


    val input = readInput("Day09").map { cmd -> cmd.split(" ").let { Pair(Direction.valueOf(it[0]), it[1].toInt()) } }

    println(solve(input, 2))
    println(solve(input, 10))
}

private enum class Direction {
    R,
    L,
    U,
    D
}