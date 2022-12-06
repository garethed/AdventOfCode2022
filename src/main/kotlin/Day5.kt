class Day5 : DayWithInputFile<String, Day5.CrateData>() {

    override fun tests() {
        test(::part1Impl, "CMZ", testData)
        test(::part2Impl, "MCD", testData)

    }

    override fun parseInput(input: String): CrateData {
        return CrateData(input)
    }

    override fun part1Impl(input: CrateData): String {

        input.moves.forEach {
            repeat (it.count) { i ->
                val crate = input.stacks[it.from - 1].removeLast()
                input.stacks[it.to - 1].add(crate)
            }
        }

        return input
            .stacks
            .map { it.last() }
            .joinToString("")
    }

    override fun part2Impl(input: CrateData): String {

        input.moves.forEach {
            val temp = ArrayDeque<Char>()
            repeat (it.count) { i ->
                temp.add(input.stacks[it.from - 1].removeLast())
            }

            temp.reversed().forEach { c ->
                input.stacks[it.to - 1].add(c)
            }
        }

        return input
            .stacks
            .map { it.last() }
            .joinToString("")
    }

    data class CrateData(val data:String) {

        val stacks = mutableListOf<ArrayDeque<Char>>()
        var moves = mutableListOf<Move>()


        init {

            val parts = splitOnBlankLines(data)

            val initial = parts[0].lines().reversed()

            for (i in 0..100) {
                val idx = 1 + i * 4
                if (idx >= initial[1].length) break;
                val stack = ArrayDeque<Char>()
                stacks.add(stack)

                for (j in 1 until initial.count()) {
                    val item = initial[j][idx]
                    if (item == ' ') break;
                    stack.add(item)
                }
            }

            moves = parts[1]
                .lines()
                .map {Move.fromLine(it)}
                .toMutableList()
        }
    }

    data class Move (val count:Int, val from:Int, val to:Int) {
        companion object {
            fun fromLine(line:String):Move {
                val modified = line
                    .replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split(' ')
                    .map { it.toInt() }
                return Move(modified[0], modified[1], modified[2])
            }
        }
    }

    var testData = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""
}