class Day13 : DayWithInputFile<Int, List<Pair<Day13.Packet, Day13.Packet>>>() {

    override fun tests() {
        //test(::part1Impl, 0, "[[[]]]\n[[]]")
        test(::part1Impl, 13, testData)
        test(::part2Impl, 140, testData)
    }

    override fun parseInput(input: String): List<Pair<Packet,Packet>> {

        return splitOnBlankLines(input)
            .map { pair ->
               Pair(buildPacket(pair.lines()[0]).first, buildPacket(pair.lines()[1]).first)
            }
    }

    private fun buildPacket(s: String): Pair<Packet,String> {
        var remaining = s

        if (s[0].isDigit()) {
            val end = s.indexOfFirst { !it.isDigit() }
            val value = s.substring(0, end).toInt()
            return Pair(Packet(null, value), s.substring(end))
        }
        else {
            assert(remaining[0] == '[')
            val items = mutableListOf<Packet>()
            while (remaining.length > 0 && remaining[0] != ']') {
                if (remaining[1] == ']') {
                    remaining = remaining.substring(1)
                }
                else {
                    val next = buildPacket(remaining.substring(1))
                    items.add(next.first)
                    remaining = next.second
                }
            }
            return Pair(Packet(items, null), if (remaining.isEmpty()) { remaining } else { remaining.substring(1) })
        }
    }

    override fun part1Impl(input: List<Pair<Packet,Packet>>): Int {
        return input
            .mapIndexed { index, pair -> if (pair.first.compareTo(pair.second) == -1) index + 1 else 0 }
            .sum()
    }

    override fun part2Impl(input: List<Pair<Packet,Packet>>): Int {
        val s1 = buildPacket("[[2]]").first
        val s2 = buildPacket("[[6]]").first

        val packets = input.flatMap { listOf(it.first, it.second) } + s1 + s2

        val sorted = packets.sorted()

        return (sorted.indexOf(s1) + 1) * (sorted.indexOf(s2) + 1)
    }

    data class Packet(val items : List<Packet>?, val value:Int?) : Comparable<Packet> {

        override fun compareTo(other:Packet) :Int {

           //println("compare $this to $other")

            if (value != null && other.value != null) {
                return value.compareTo(other.value)
            }
            else {
                var i = 0
                val left = this.toListPacket()
                val right = other.toListPacket()
                while (true) {
                    if (i >= left.items!!.size) {
                        if (i >= right.items!!.size) {
                            //println ("0")
                            return 0
                        }
                        //println ("-1")
                        return -1
                    }
                    else if (i >= right.items!!.size) {
                        //println ("1")
                        return 1
                    }
                    val thisComparison = left.items[i].compareTo(right.items[i])
                    if (thisComparison != 0) {
                        //println(thisComparison)
                        return thisComparison
                    }
                    i++
                }
            }
        }

        fun toListPacket() : Packet {
            return if (items == null) { Packet(listOf(this), null) } else this
        }

        override fun toString(): String {
            return value?.toString() ?: ("[" + items!!.map { it.toString() }.joinToString(",") + "]")
        }
    }

    var testData = """[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]"""
}