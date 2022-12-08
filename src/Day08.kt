fun main() {

    fun visualTrees(trees: List<Tree>): Int {
        var currentHeight = -1
        var visibleTrees = 0
        for (tree in trees) {
            if (tree.treeHeight > currentHeight) {
                currentHeight = tree.treeHeight
                if (!tree.seen) {
                    visibleTrees = visibleTrees.inc()
                    tree.seen = true
                }
            }
        }
        return visibleTrees
    }

    fun calcScore(trees: List<List<Tree>>, startingPoint: Pair<Int, Int>): Int {
        var score = 1
        var tempScore = 0
        val treeSize = trees[startingPoint.first][startingPoint.second].treeHeight
        for (x in (startingPoint.first + 1 until trees[startingPoint.first].size).toList()) {
            tempScore = tempScore.inc()
            if (trees[x][startingPoint.second].treeHeight >= treeSize) {
                break
            }
        }
        score *= tempScore
        tempScore = 0
        for (x in (startingPoint.first - 1 downTo 0).toList()) {
            tempScore = tempScore.inc()
            if (trees[x][startingPoint.second].treeHeight >= treeSize) {
                break
            }
        }
        score *= tempScore
        tempScore = 0
        for (y in (startingPoint.second + 1 until trees[startingPoint.first].size).toList()) {
            tempScore = tempScore.inc()
            if (trees[startingPoint.first][y].treeHeight >= treeSize) {
                break
            }
        }
        score *= tempScore
        tempScore = 0
        for (y in (startingPoint.second - 1 downTo 0).toList()) {
            tempScore = tempScore.inc()
            if (trees[startingPoint.first][y].treeHeight >= treeSize) {
                break
            }
        }
        score *= tempScore

        return score
    }


    fun part1(rows: List<List<Tree>>): Int {

        val cols = (rows.indices).map { (rows[it].indices).map { i -> rows[i][it] } }

        return rows.drop(1).dropLast(1).sumOf { visualTrees(it) } +
                rows.drop(1).dropLast(1).sumOf { visualTrees(it.reversed()) } +
                cols.drop(1).dropLast(1).sumOf { visualTrees(it) } +
                cols.drop(1).dropLast(1).sumOf { visualTrees(it.reversed()) } +
                4
    }

    fun part2(input: List<List<Tree>>): Int {
        return (input.indices).maxOf { (input[it].indices).maxOf { y -> calcScore(input, Pair(it, y)) } }
    }

    val input = readInput("Day08").map { it.toList().map { c -> Tree(c.digitToInt()) } }
    println(part1(input))
    println(part2(input))
}

data class Tree(val treeHeight: Int, var seen: Boolean = false)
