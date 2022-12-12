import java.awt.Point
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val alphabet = ('a'..'z').joinToString ("")

    fun genPoints(input: List<String>): MapGraph{
        val points = mutableMapOf<Point, MapPoint>()

        for (x in input.indices){
            for (y in input[x].indices){
                var height = input[x][y]

                if(height == 'S')
                    height = 'a'

                if(height == 'E')
                    height = 'z'

                points[Point(x,y)] = MapPoint(pos = Point(x,y), height = alphabet.indexOfFirst { it == height} + 1)
            }
        }

        val mapGraph = MapGraph(points = points)

        for (x in input.indices){
            for (y in input[x].indices){
                val workingPoint: MapPoint = points.getOrElse(Point(x,y)){throw IllegalStateException()}
                mapGraph.edges[workingPoint] = mutableListOf()
                for (add in listOf(-1,1)){
                    points[Point(add + x, y)]?.takeIf { it.height - workingPoint.height <= 1 }?.let { mapGraph.edges[workingPoint]?.add(it) }
                    points[Point(x, add + y)]?.takeIf { it.height - workingPoint.height <= 1 }?.let { mapGraph.edges[workingPoint]?.add(it) }
                }
            }
        }

        return mapGraph
    }

    fun getFirstWithChar(input: List<String>, char: Char): Point{
        for (x in input.indices) {
            for (y in input[x].indices) {
                if (input[x][y] == char)
                    return Point(x,y)
            }
        }
        return Point(-1,-1)
    }

    fun searchPath(mapGraph: MapGraph, start: MapPoint, end: MapPoint): Map<MapPoint, Optional<MapPoint>>{
        val queue = ArrayDeque(listOf(start))
        val cameFrom = mutableMapOf<MapPoint, Optional<MapPoint>>(start to Optional.empty())

        while (queue.isNotEmpty()){
            val pos = queue.removeFirst()

            if (pos == end)
                return cameFrom

            for (neighbor in mapGraph.getNeighbors(pos))
                if (!cameFrom.keys.contains(neighbor)){
                    queue.add(neighbor)
                    cameFrom[neighbor] = Optional.of(pos)
                }
        }

        return cameFrom
    }

    fun getPath(paths: Map<MapPoint, Optional<MapPoint>>, end: MapPoint): List<MapPoint>{
        val path = mutableListOf(end)
        var currentPos = end
        while (paths[currentPos]?.isPresent == true){
            path.add(paths[currentPos]?.get()!!)
            currentPos = paths[currentPos]?.get()!!
        }
        return path
    }

    fun part1(input: List<String>): Int {
        val graph = genPoints(input)
        val start = graph.points[getFirstWithChar(input, 'S')]!!
        val end = graph.points[getFirstWithChar(input, 'E')]!!

        val paths = searchPath(graph, start, end)
        val startToEnd = getPath(paths, end)

        return startToEnd.size - 1
    }

    fun part2(input: List<String>): Int {
        val graph = genPoints(input)
        val end = graph.points[getFirstWithChar(input, 'E')]!!

        return graph.points.values.asSequence()
            .filter{it.height == 1}
            .map { getPath(searchPath(graph, it, end), end) }
            .filter { it.size > 1 }
            .minOf { it.size - 1 }
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

data class MapPoint(val pos: Point, val height: Int)

data class MapGraph(val points: Map<Point, MapPoint>, val edges: MutableMap<MapPoint, MutableList<MapPoint>> = mutableMapOf()){

    fun getNeighbors(id: MapPoint): List<MapPoint>{
        return edges[id]?.toList() ?: listOf()
    }
}