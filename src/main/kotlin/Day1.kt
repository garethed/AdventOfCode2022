class Day1 : DayWithInputFile<Int, List<List<Int>>>() {

    override fun tests() {
        test(::part1Impl, 24000, testData)
        test(::part2Impl, 45000, testData)

    }

    override fun parseInput(input: String): List<List<Int>> {
        return splitOnBlankLines(input)
            .map {
                    it.lines().map { l -> Integer.parseInt(l)}}
    }

    override fun part1Impl(input: List<List<Int>>): Int {
        return input.map { it.sum() }.maxOrNull()!!
    }

    override fun part2Impl(input: List<List<Int>>): Int {
        return input.map { it.sum() }.sortedDescending().take(3).sum()
    }

    var testData = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""
}