class Day3 : DayWithInputFile<Int, List<Day3.Rucksack>>() {

    override fun tests() {
        test(::part1Impl, 157, testData)
        test(::part2Impl, 70, testData)

    }

    override fun parseInput(input: String): List<Rucksack> {
        return input
            .lines()
            .map { Rucksack(it)}
    }

    override fun part1Impl(input: List<Rucksack>): Int {
        return input.sumOf { priority(it.common.first()) }
    }

    override fun part2Impl(input: List<Rucksack>): Int {
        val groups = input.windowed(3, 3)

        return groups
            .map { g ->
                g.map {
                    it.data.toCharArray().toHashSet()
                }
            }
            .map {
                 priority(it[0].intersect(it[1]).intersect(it[2]).first())
            }
            .sum()
    }

    fun priority(item: Char): Int {
        return if (item.isLowerCase()) {
            item.code - 'a'.code + 1
        } else {
            item.code - 'A'.code + 27
        }
    }

    data class Rucksack(val data:String) {

        val compartment1 = data.substring(0, data.length / 2)
        val compartment2 = data.substring(data.length / 2)

        val common = compartment1.toCharArray().toHashSet()
            .intersect(compartment2.toCharArray().toHashSet())


    }


    var testData = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""
}