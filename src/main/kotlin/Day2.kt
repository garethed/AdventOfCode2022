class Day2 : DayWithInputFile<Int, List<Day2.Round>>() {

    override fun tests() {
        test(::part1Impl, 15, testData)
        test(::part2Impl, 12, testData)

    }

    override fun parseInput(input: String): List<Round> {
        return input
            .lines()
            .map { Round(it)}
    }

    override fun part1Impl(input: List<Round>): Int {
        return input.sumOf { it.score() }
    }

    override fun part2Impl(input: List<Round>): Int {
        return input.sumOf { it.alternateScore() }
    }

    data class Round(val data:String) {

        fun score():Int {
            return scores[data]!!
        }

        fun alternateScore():Int {
            return alternateScores[data]!!
        }


        val scores = hashMapOf(
            "A X" to 1 + 3,
            "A Y" to 2 + 6,
            "A Z" to 3,
            "B X" to 1,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 1 + 6,
            "C Y" to 2,
            "C Z" to 3 + 3,
        )

        //RPS
        //LDW

        val alternateScores = hashMapOf(
            "A X" to 3,
            "A Y" to 1 + 3,
            "A Z" to 2 + 6,
            "B X" to 1,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 2,
            "C Y" to 3 + 3,
            "C Z" to 1 + 6,
        )
    }

    var testData = """A Y
B X
C Z"""
}