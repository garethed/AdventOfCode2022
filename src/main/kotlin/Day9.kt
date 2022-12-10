class Day9 : DayWithInputFile<Int, List<Point>>() {

    override fun tests() {
        test(::part1Impl, 13, testData)
        test(::part2Impl, 36, testData2)
    }

    override fun parseInput(input: String): List<Point> {
        return input
            .lines()
            .map { Point.direction(it[0]) * it.substring(2).toInt() }
    }

    override fun part1Impl(input: List<Point>): Int {
        var head = Point(0,0)
        var tail = Point(0,0)

        var visited = HashSet<Point>()
        visited.add(tail)

        input.forEach {
            repeat (Math.abs(it.x) + Math.abs(it.y)) { o ->
                head += it.sign()
                tail = moveTail(head, tail)
                visited.add(tail)
            }
        }

        return visited.count()
    }

    fun moveTail(head:Point, tail:Point) : Point {
        val delta = head - tail
        if (Math.abs(delta.x) > 1 || Math.abs(delta.y) > 1) {
            return tail + delta.sign()
        }
        return tail
    }

    override fun part2Impl(input: List<Point>): Int {
        val knots = Array(10) { _ -> Point(0, 0) }
        var visited = HashSet<Point>()
        visited.add(knots[9])

        input.forEach {
            repeat (Math.abs(it.x) + Math.abs(it.y)) { o ->
                knots[0] = knots[0] + it.sign()

                for (i in 1..9) {
                    knots[i] = moveTail(knots[i - 1], knots[i])
                    if (i == 9) {
                        visited.add(knots[i])
                    }
                }
            }
        }

        return visited.count()
    }

    var testData = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"""

    var testData2 = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"""
}