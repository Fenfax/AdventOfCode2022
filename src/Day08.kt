fun main() {

    fun visualTrees(trees: List<Tree>): Int {
        var currentHeight = -1
        var visibleTrees = 0
        for (tree in trees) {
            if (tree.treeHeight > currentHeight) {
                currentHeight = tree.treeHeight
                if(!tree.seen){
                    visibleTrees = visibleTrees.inc()
                    tree.seen = true
                }
            }
        }
        return visibleTrees
    }


    fun part1(rows: List<List<Tree>>): Int {

        val cols = (rows.indices).map { (rows[it].indices).map { i -> rows[i][it] } }

        return rows.drop(1).dropLast(1).sumOf { visualTrees(it) } +
                rows.drop(1).dropLast(1).sumOf { visualTrees(it.reversed()) } +
                cols.drop(1).dropLast(1).sumOf { visualTrees(it) } +
                cols.drop(1).dropLast(1).sumOf { visualTrees(it.reversed()) } +
                4
    }

    val input = readInput("Day08").map { it.toList().map { c -> Tree(c.digitToInt()) } }
    println(part1(input))
}

data class Tree(val treeHeight: Int, var seen: Boolean = false)
