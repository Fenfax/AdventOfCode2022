import org.apache.commons.lang3.StringUtils

fun main() {
    fun game(playerOne: String, playerTwo: String): Pair<GameResult,Token> {
        val pOne = Token.getByToken(playerOne)
        val pTwo = Token.getByToken(playerTwo)

        if (pOne == pTwo) return Pair(GameResult.DRAW, pTwo)
        if (pOne.willLose(pTwo)) return Pair(GameResult.PLAYER_TWO, pTwo)
        return Pair(GameResult.PLAYER_ONE, pTwo)
    }

    fun calcToken(playerOne: String, decidingToken: String): Pair<GameResult,Token> {
        val pOne = Token.getByToken(playerOne)

        return when (GameResult.getByDecidingToken(decidingToken)){
            GameResult.DRAW -> Pair(GameResult.DRAW, pOne)
            GameResult.PLAYER_ONE -> Pair(GameResult.PLAYER_ONE, Token.values().first { it.willLose(pOne) })
            GameResult.PLAYER_TWO -> Pair(GameResult.PLAYER_TWO, Token.values().first { pOne.willLose(it)})
        }

    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(StringUtils.SPACE) }
            .map { game(it[0], it[1]) }
            .sumOf { with(it){ first.score + second.score} }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(StringUtils.SPACE) }
            .map { calcToken(it[0], it[1]) }
            .sumOf { with(it){ first.score + second.score} }
    }


    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class GameResult(val score: Int, val decidingToken: String) {
    PLAYER_ONE(0,"X"),
    PLAYER_TWO(6, "Z"),
    DRAW(3,"Y");

    companion object {
        fun getByDecidingToken(input: String): GameResult {
            return values().first { it.decidingToken == input }
        }
    }
}

enum class Token(val stringTokens: List<String>, val score: Int) {
    ROCK(listOf("A", "X"), 1),
    PAPER(listOf("B", "Y"), 2),
    SCISSORS(listOf("C", "Z"),3);

    fun willLose(vsToken: Token): Boolean{
        return loses[this]?.equals(vsToken) ?: true
    }

    companion object {
        fun getByToken(input: String): Token {
            return values().first { it.stringTokens.contains(input) }
        }

        private val loses: Map<Token, Token> = mapOf(
            Pair(ROCK, PAPER),
            Pair(PAPER, SCISSORS),
            Pair(SCISSORS, ROCK)
        )
    }
}
