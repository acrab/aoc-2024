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

fun up(start: Point): Point? = if (start.second == 0) null else Point(start.first, start.second - 1)
fun <T> Grid<T>.down(start: Point): Point? =
    if (start.second == size - 1) null else Point(start.first, start.second + 1)

fun left(start: Point): Point? = if (start.first == 0) null else Point(start.first - 1, start.second)
fun <T> Grid<T>.right(start: Point): Point? =
    if (start.first == get(0).size - 1) null else Point(start.first + 1, start.second)

fun <T> Grid<T>.cardinals(start: Point) = listOfNotNull(up(start), down(start), left(start), right(start))

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

fun <T> Grid<T>.forEachPoint(action: (T, Point) -> Unit) {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, t ->
            action(t, Point(x, y))
        }
    }
}

/**
 * If an entry for `key` exists, adds `value` to its list.
 * If there's no entry, adds a new list for that key, with `value` as it's only member
 */
fun <T, U> MutableMap<T, MutableList<U>>.upsert(key: T, value: U) {
    if (containsKey(key)) {
        get(key)?.add(value)
    } else {
        set(key, mutableListOf(value))
    }
}

fun <T> MutableMap<T, Long>.addOrSet(key: T, value: Long) = set(key, (get(key) ?: 0) + value)

fun <K, V> Map<K, V>.sumOf(calculation: (Map.Entry<K, V>) -> Long): Long {
    var out = 0L
    entries.forEach { out += calculation(it) }
    return out
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

fun <T> Iterable<T>.sumOfIndexed(selector: (T, Int) -> Int): Int {
    var sum = 0
    forEachIndexed { index, t -> sum += selector(t, index) }
    return sum
}

fun <T> Iterable<T>.sumOfIndexed(selector: (T, Long) -> Long): Long {
    var sum = 0L
    forEachIndexed { index, t -> sum += selector(t, index.toLong()) }
    return sum
}