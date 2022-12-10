class Day8 : DayWithInputFile<Int, Grid>() {

    override fun tests() {
        test(::part1Impl, 21, testData)
        test(::part2Impl, 8, testData)
    }

    override fun parseInput(input: String): Grid {
        return Grid(input
            .lines()
            .map { it.map { c -> c.digitToInt() }.toIntArray() }
            .toTypedArray())
    }

    override fun part1Impl(input: Grid): Int {
        return input
            .points()
            .count { visible(it, input) }
    }

    fun visible(p: Point, input: Grid) : Boolean {
        return Point
            .directions
            .any {
                (input.toEdge(p, it).maxOfOrNull { p2 -> input[p2] } ?: -1) < input[p]
            }
    }

    fun scenicScore(p: Point, input: Grid) : Int {
        return Point.directions.map { scenicScore(p, it, input) }.reduce { acc, i -> acc * i }

    }

    fun scenicScore(p: Point, d:Point, input: Grid) : Int {
        val toEdge = input.toEdge(p, d).toList()

        return Math.min(
            toEdge.takeWhile { input[it] < input[p] }.count() + 1,
            toEdge.count())
    }

    override fun part2Impl(input: Grid): Int {
        println(scenicScore(Point(2,1),input))
        return input
            .points()
            .maxOf { scenicScore(it, input) }
    }

    var testData = """30373
25512
65332
33549
35390"""
}