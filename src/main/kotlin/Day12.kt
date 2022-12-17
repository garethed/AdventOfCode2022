class Day12 : DayWithInputFile<Int, Day12.HeightMap>() {

    override fun tests() {
        test(::part1Impl, 31, testData)
        test(::part2Impl, 29, testData)
    }

    override fun parseInput(input: String): HeightMap {
        val lines = input.lines()
        val grid = Grid(lines[0].length, input.lines().size)
        var start = Point(0,0)
        var end = Point(0,0)

        lines.forEachIndexed { y, l ->
            l.forEachIndexed { x, c ->
                val v = when(c) {
                    'S' -> 0
                    'E' -> 25
                    else -> c.code - 'a'.code
                }
                grid[x,y] = v

                if (c == 'S') {
                    start = Point(x,y)
                }
                if (c == 'E') {
                    end = Point(x,y)
                }
            }
        }

        return HeightMap(grid, start, end)
    }

    override fun part1Impl(input: HeightMap): Int {

        val distances = Grid(input.grid.w, input.grid.h)
        val queue = mutableListOf(input.start)

        while (queue.isNotEmpty()) {
            val next = queue.removeAt(0)
            val distance = distances[next]
            val height = input.grid[next]

            next.adjacent.forEach {
                if (input.grid.contains(it) && distances[it] == 0 && input.grid[it] <= height + 1) {
                    queue.add(it)
                    distances[it] = distance + 1
                }
            }
        }

        return distances[input.end]

    }

    override fun part2Impl(input: HeightMap): Int {
        return input.grid.points()
            .filter { input.grid[it] == 0 }
            .map { part1Impl(HeightMap(input.grid, it, input.end)) }
            .filter { it > 0 }
            .minOrNull()!!
    }

    data class HeightMap(val grid: Grid, val start:Point, val end:Point)

    var testData = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""
}