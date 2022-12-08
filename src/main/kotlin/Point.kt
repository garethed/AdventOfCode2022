import java.lang.Integer.parseInt

data class Point(val x:Int, val y:Int) {

    val adjacent:Sequence<Point> get()
        = sequenceOf(Point(x - 1, y), Point(x, y - 1), Point(x + 1, y), Point(x, y + 1))

    val adjacentWithDiagonals:Sequence<Point> get()
        =  adjacent.plus(sequenceOf(Point(x - 1, y - 1), Point(x + 1, y - 1), Point(x + 1, y + 1), Point(x - 1, y + 1)))


    companion object {
        fun fromString(string: String) : Point {
            val parts = string.split(",").map { parseInt(it) }
            return Point(parts[0], parts[1])
        }

        val directions = arrayOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))
    }

    operator fun plus(other:Point) : Point {
        return Point(x + other.x, y + other.y)
    }
}

data class Rect(val xmin:Int, val xmax:Int, val ymin:Int, val ymax:Int) {
    fun contains(x: Int, y: Int): Boolean {
        return x in xmin..xmax && y in ymin..ymax
    }
}

class Grid(val w:Int, val h:Int, val data : Array<IntArray>? = null) {

    constructor(array: Array<IntArray>?) : this(array!![0].size, array.size, array)

    fun points(): Sequence<Point> {
        return sequence {
            for (y in 0 until h) {
                for (x in 0 until w) {
                    yield(Point(x, y))
                }
            }
        }
    }

    fun toEdge(p:Point, d:Point) : Sequence<Point> {
        var next = p + d
        return sequence {
            while (contains(next)) {
                yield(next)
                next += d
            }
        }
    }

    fun contains(p:Point) : Boolean {
        return p.x >= 0 && p.y >= 0 && p.x < w && p.y < h
    }

    fun adjacent(p:Point): Sequence<Point> {
        return p.adjacent.filter { contains(it) }
    }

    fun adjacentWithDiagonals(p:Point): Sequence<Point> {
        return p.adjacentWithDiagonals.filter { contains(it) }
    }


    operator fun get(p:Point) : Int {
        return data!![p.y][p.x]
    }

    operator fun set(p:Point, i:Int) {
        data!![p.y][p.x] = i
    }
}


data class Line(val p1:Point, val p2:Point) {
}