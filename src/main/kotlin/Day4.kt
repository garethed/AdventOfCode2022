class Day4 : DayWithInputFile<Int, List<Day4.Assignment>>() {

    override fun tests() {
        test(::part1Impl, 2, testData)
        test(::part2Impl, 4, testData)

    }

    override fun parseInput(input: String): List<Assignment> {
        return input
            .lines()
            .map { Assignment(it)}
    }

    override fun part1Impl(input: List<Assignment>): Int {
        return input.count { it.fullyContained }
    }

    override fun part2Impl(input: List<Assignment>): Int {
        return input.count() { it.overlaps }
    }

    data class Assignment(val data:String) {

        val first = range(data.split(',')[0])
        val second = range(data.split(',')[1])

        private fun range(input:String) : IntRange {
            val low = input.split('-')[0].toInt()
            val high = input.split('-')[1].toInt()
            return IntRange(low, high)
        }

        private fun rangeContains(outer:IntRange, inner:IntRange): Boolean {
            return outer.contains(inner.first) && outer.contains(inner.last)
        }

        private fun rangeOverlaps(left:IntRange, right:IntRange): Boolean {
            return right.contains(left.last) || left.contains(right.last)
        }

        val fullyContained = rangeContains(first, second) || rangeContains(second, first)
        val overlaps =  rangeOverlaps(first, second)
    }


    var testData = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""
}