class Day11 : DayWithInputFile<Long, List<Day11.Monkey>>() {

    override fun tests() {
        test(::part1Impl, 10605, testData)
        test(::part2Impl, 2713310158, testData)
    }

    override fun parseInput(input: String): List<Monkey> {
        return splitOnBlankLines(input).map { parseMonkey(it) }
    }

    private fun parseMonkey(data: String) : Monkey {
        val items = extractGroupsFromRegex("""Monkey (\d+):
  Starting items: (.+)
  Operation: new = (\w+) (.) (\w+)
  Test: divisible by (\d+)
    If true: throw to monkey (\d+)
    If false: throw to monkey (\d+)""", data)

        return Monkey(
            parseOperation(items[2], items[3], items[4]),
            items[5].toLong(),
            items[1].split(", ").map { Item(it.toLong()) }.toList(),
            items[6].toInt(),
            items[7].toInt()
        )
    }

    private fun parseOperation(p1: String, op: String, p2: String): (Long) -> Long {
        return { i:Long ->
            val v1 = if (p1 == "old") { i } else p1.toLong()
            val v2 = if (p2 == "old") { i } else p2.toLong()
            if (op == "+") { v1 + v2 } else { v1 * v2 }
        }
    }

    override fun part1Impl(input: List<Monkey>): Long {
        repeat(20) {
            input.forEach {
                it.processItems(input)
            }
        }

        val counts =  input.map { it.count }.sorted().reversed()
        return counts[0].toLong() * counts[1].toLong()
    }

    override fun part2Impl(input: List<Monkey>): Long {
        val lcm = input.map { it.divisor }.toHashSet().reduce {acc, i -> acc * i}
        input.forEach { it.reduceBy = 1; it.lcm = lcm }
        
        repeat(10000) {
            input.forEach {
                it.processItems(input)
            }
        }

        val counts =  input.map { it.count }.sorted().reversed()
        return counts[0].toLong() * counts[1].toLong()
    }

    class Item (var worryLevel : Long) { }

    class Monkey(
        private val operation: (Long) -> Long,
        val divisor: Long,
        initialItems: Iterable<Item>,
        private val trueTarget: Int,
        private val falseTarget: Int) {

        val items = initialItems.toMutableList()
        var count = 0
        var reduceBy = 3
        var lcm = 10000000L

        fun processItems(allMonkeys : List<Monkey>) {
            while (items.any()) {
                count++
                val next = items.removeAt(0)
                next.worryLevel = operation.invoke(next.worryLevel)
                next.worryLevel /= reduceBy
                next.worryLevel = next.worryLevel % lcm

                if (next.worryLevel % divisor == 0L) {
                    allMonkeys[trueTarget].items.add(next)
                }
                else {
                    allMonkeys[falseTarget].items.add(next)
                }
            }
        }
    }

    var testData = """Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1"""
}