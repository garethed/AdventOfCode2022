class Day10 : DayWithInputFile<Int, List<Day10.Command>>() {

    override fun tests() {
        test(::part1Impl, 13140, testData)
        test(::part2Impl, 0, testData)
    }

    override fun parseInput(input: String): List<Command> {
        return input
            .lines()
            .map {
                val parts = it.split(' ')
                Command(parts[0], if (parts.size > 1) { parts[1].toInt() } else null )
            }
    }

    override fun part1Impl(input: List<Command>): Int {

        x = 1
        var pc = 0
        var cycles = 0

        var total = 0


        while (pc < input.size) {
            cycles++

            if ((cycles - 20) % 40 == 0 && cycles <= 220) {
                total += cycles * x
            }

            if (input[pc].execute()) { pc++ }
        }

        return total
    }

    override fun part2Impl(input: List<Command>): Int {

        x = 1
        var pc = 0
        var cycles = 0

        while (pc < input.size) {
            cycles++
            val pos = ((cycles - 1) % 40) + 1
            if (pos == 1) println()

            val delta = pos - x

            if (delta in 0..2) {
                print('#')
            }
            else {
                print('.')
            }

            if (input[pc].execute()) { pc++ }
        }

        return 0

    }

    var x:Int = 1

    inner class Command(val instruction:String, val param:Int?) {

        var cycles = 0

        fun execute() : Boolean {
            cycles++
            if (instruction == "noop") {
                return true
            }
            else {
                if (cycles == 2) {
                    x += param!!
                    return true
                }
            }
            return false
        }

    }

    var testData = """addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop"""
}