class Day7 : DayWithInputFile<Int, Day7.Node>() {

    override fun tests() {
        test(::part1Impl, 95437, testData)
        test(::part2Impl, 24933642, testData)
    }

    override fun parseInput(input: String): Node {
        val root = Node("/", null)
        var current = root

        input
            .lines()
            .forEach {

                val parts = it.split(' ')

                when {
                    it == "$ ls" -> {}
                    it == "$ cd /" -> current = root
                    it == "$ cd .." -> current = current.parent!!
                    it.startsWith("$ cd") -> current = current.children[parts[2]]!!
                    it.startsWith("dir") -> current.children[parts[1]] = Node(parts[1], current)
                    else -> current.files[parts[1]] = parts[0].toInt()
                }
            }

        return root
    }

    override fun part1Impl(input: Node): Int {
        return input.all()
            .map { it.size() }
            .filter { it <= 100000 }
            .sum()
    }

    override fun part2Impl(input: Node): Int {
        val total = 70000000
        val needed = 30000000
        val to_free = needed - (total - input.size())

        return input.all()
            .map { it.size() }
            .filter { it >= to_free }
            .minOrNull()!!
    }

    data class Node(val name: String, val parent: Node?) {
        val children = mutableMapOf<String,Node>()
        val files = mutableMapOf<String,Int>()

        fun size():Int {
            return files.values.sum() + children.values.map { it.size() }.sum()
        }

        fun all():Sequence<Node> {
            return sequenceOf(this) + children.values.asSequence().flatMap { it.all() }
        }
    }

    var testData = """$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"""
}