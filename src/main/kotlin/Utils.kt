/**
 * Reads lines from the given input txt file.
 */


fun readFile(name: String) =
    object {}.javaClass.getResourceAsStream(name)?.bufferedReader()?.readLines() ?: error("Unable to read file $name!")

fun readInput(name: String) = readFile("$name/input.txt")
fun readTest(name: String) = readFile("$name/test.txt")

fun <O, L, R> List<O>.unzip(transform: (O) -> Pair<L, R>): Pair<List<L>, List<R>> {
    val left = mutableListOf<L>()
    val right = mutableListOf<R>()
    forEach {
        val (l, r) = transform(it)
        left.add(l)
        right.add(r)
    }
    return left to right
}

typealias Grid<T> = List<List<T>>

fun <T> Grid<T>.get(x: Int, y: Int): T = this[y][x]

fun <T> Grid<T>.countAll(predicate: (T) -> Boolean) = sumOf { it.count(predicate) }

typealias Point = Pair<Int, Int>

fun <T> Grid<T>.get(point: Point): T = this[point.second][point.first]

fun <T> Grid<T>.positionOfFirst(predicate: (T) -> Boolean): Point {
    forEachIndexed { index, row ->
        val position = row.indexOfFirst(predicate)
        if (position != -1) {
            return Point(position, index)
        }
    }
    return Point(-1, -1)
}

enum class Direction {
    UP,
    LEFT,
    DOWN,
    RIGHT;

    fun turnClockwise() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}