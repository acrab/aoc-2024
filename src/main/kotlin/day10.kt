fun main() {

    fun findPeaks(map: Grid<Char>, currentPoint: Point, currentHeight: Char): Set<Point> =
        if (currentHeight == '9') setOf(currentPoint)
        else map.cardinals(currentPoint)
            .filter { map.get(it) == currentHeight + 1 }
            .flatMapTo(mutableSetOf()) { findPeaks(map, it, currentHeight + 1) }

    fun partOne(input: List<String>): Int {
        val map: Grid<Char> = input.map { it.toList() }
        var count = 0
        map.forEachPoint { c, coordinates ->
            if (c == '0') {
                count += findPeaks(map, coordinates, c).size
            }
        }
        return count
    }

    fun findRoutes(map: Grid<Char>, currentPoint: Point, currentHeight: Char): Int =
        if (currentHeight == '9') 1
        else map.cardinals(currentPoint)
            .filter { map.get(it) == currentHeight + 1 }
            .sumOf { findRoutes(map, it, currentHeight + 1) }

    fun partTwo(input: List<String>): Int {
        val map: Grid<Char> = input.map { it.toList() }
        var count = 0
        map.forEachPoint { c, coordinates ->
            if (c == '0') {
                count += findRoutes(map, coordinates, c)
            }
        }
        return count
    }

    val test = readTest("day10")
    val part1 = partOne(test)
    assert(part1 == 36) { "Expected 36, got $part1" }
    assert(partTwo(test) == 81)
    val input = readInput("day10")
    println(partOne(input))
    println(partTwo(input))
}