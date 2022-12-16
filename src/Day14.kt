import java.awt.Point

fun main() {

    val startPoint = Point(500, 0)

    fun buildWalls(input: List<String>): Set<Point> {
        return input.flatMap { row ->
            row.split("->")
                .map { cord ->
                    cord.split(",")
                        .map { it.trim().toInt() }
                        .let { Point(it[0], it[1]) }
                }
                .zipWithNext { current, next ->
                    (minOf(current.x, next.x)..maxOf(current.x, next.x))
                        .flatMap { x ->
                            (minOf(current.y, next.y)..maxOf(current.y, next.y))
                                .map { y ->
                                    Point(x, y)
                                }
                        }
                }.flatten()
                .toSet()
        }.toSet()
    }

    fun nextPoint(currentPoint: Point, occupiedPoints: Set<Point>, maxY: Int): Point {
        val nextPoint: Point = currentPoint.location
        if (nextPoint.y + 1 == maxY) {
            return nextPoint
        }
        nextPoint.translate(0, 1)
        if (!occupiedPoints.contains(nextPoint))
            return nextPoint(nextPoint, occupiedPoints, maxY)
        nextPoint.translate(-1, 0)
        if (!occupiedPoints.contains(nextPoint))
            return nextPoint(nextPoint, occupiedPoints, maxY)
        nextPoint.translate(2, 0)
        if (!occupiedPoints.contains(nextPoint))
            return nextPoint(nextPoint, occupiedPoints, maxY)
        return currentPoint
    }

    fun part1(input: List<String>): Int {
        val occupiedPoints = buildWalls(input).toMutableSet()

        val maxY = occupiedPoints.maxOf { it.y } + 1

        var stepCount = 0
        do {
            stepCount += 1
            val nextPoint = nextPoint(startPoint, occupiedPoints, maxY)
            occupiedPoints.add(nextPoint)
        } while (nextPoint.y <= maxY - 2)


        return stepCount - 1
    }

    fun part2(input: List<String>): Int{
        val occupiedPoints = buildWalls(input).toMutableSet()

        val maxY = occupiedPoints.maxOf { it.y } + 2

        var stepCount = 0
        do {
            stepCount += 1
            val nextPoint = nextPoint(startPoint, occupiedPoints, maxY)
            occupiedPoints.add(nextPoint)
        } while (nextPoint != startPoint)

        return stepCount
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
