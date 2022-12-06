class Day6 : DayWithInputFile<Int, String>() {

    override fun tests() {
        test(::part1Impl, 7, input = testData)
        test(::part2Impl, 19, input = testData)
    }

    override fun parseInput(input: String): String {
        return input
    }

    override fun part1Impl(input: String): Int {
        input.windowed(4)
            .forEachIndexed { index, window ->
                if (distinct(window)) {
                    println(window)
                    return index + 4
                }
            }

        return -1
    }

    private fun distinct(window: String): Boolean {
        return window.toSet().count() == window.length
    }

    override fun part2Impl(input: String): Int {
        input.windowed(14)
            .forEachIndexed { index, window ->
                if (distinct(window)) {
                    println(window)
                    return index + 14
                }
            }

        return -1

    }

    var testData = """mjqjpqmgbljsphdztnvjfqwrcgsmlb"""
}