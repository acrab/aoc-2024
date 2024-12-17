fun main() {

    data class VisitableGridPoint(
        val value: Char,
        var visited: Boolean,
    )

    fun partOnePricing(cropType: Char, startPoint: Point, map: Grid<VisitableGridPoint>): Pair<Long, Long> {
        val cardinals = map.cardinals(startPoint)
        var fenceCount = 4L - cardinals.size
        var areaCount = 1L
        map.get(startPoint).visited = true
        cardinals.map { it to map.get(it) }.forEach { (point, data) ->
            if (data.value != cropType) {
                fenceCount++
            } else if (!data.visited) {
                val (fences, area) = partOnePricing(cropType, point, map)
                fenceCount += fences
                areaCount += area
            }
        }

        return fenceCount to areaCount
    }

    fun partOne(input: List<String>): Long {
        val map: Grid<VisitableGridPoint> = input.map { row -> row.toList().map { VisitableGridPoint(it, false) } }
        return map.sumOfPoints { visitableGridPoint, coordinates ->
            if (visitableGridPoint.visited) 0L
            else {
                val (fences, area) = partOnePricing(visitableGridPoint.value, coordinates, map)
                fences * area
            }
        }
    }

    data class FencedGridPoint(
        val value: Char,
        var visited: Boolean,
        val fenceSides: MutableList<Direction> = mutableListOf(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
        )
    )

    fun partTwoFencing(cropType: Char, startPoint: Point, map: Grid<FencedGridPoint>) {
        val cardinals = map.directionalCardinals(startPoint)
        val startGridPoint = map.get(startPoint)
        startGridPoint.visited = true
        cardinals
            .mapNotNull { (k, v) -> v?.let { k to map.get(it) } }
            .forEach { (direction, data) ->
                if (data.value == cropType) {
                    startGridPoint.fenceSides.remove(direction)
                }
            }
    }

    fun followFence(
        direction: Direction,
        fenceDirection: Direction,
        cropType: Char,
        startPoint: Point,
        map: Grid<FencedGridPoint>
    ) {
        var next = map.move(startPoint, direction)
        while (next != null) {
            val nextPoint = map.get(next)
            if (nextPoint.value != cropType) {
                break
            }
            if (nextPoint.fenceSides.contains(fenceDirection)) {
                nextPoint.fenceSides.remove(fenceDirection)
                next = map.move(next, direction)
            } else {
                break
            }
        }
    }


    fun partTwoPricing(cropType: Char, startPoint: Point, map: Grid<FencedGridPoint>): Pair<Long, Long> {
        val cardinals = map.cardinals(startPoint).map { it to map.get(it) }
        val startGridPoint = map.get(startPoint)
        startGridPoint.visited = true
        var sides = 0L
        var area = 1L
        startGridPoint.fenceSides.forEach {
            sides += 1
            when (it) {
                Direction.UP,
                Direction.DOWN -> {
                    followFence(Direction.LEFT, it, cropType, startPoint, map)
                    followFence(Direction.RIGHT, it, cropType, startPoint, map)
                }

                Direction.LEFT,
                Direction.RIGHT -> {
                    followFence(Direction.UP, it, cropType, startPoint, map)
                    followFence(Direction.DOWN, it, cropType, startPoint, map)
                }
            }

        }
        cardinals.forEach { (point, gridPoint) ->
            if (!gridPoint.visited && gridPoint.value == cropType) {
                val (s, a) = partTwoPricing(cropType, point, map)
                sides += s
                area += a
            }
        }
        return sides to area
    }

    fun partTwo(input: List<String>): Long {
        val map: Grid<FencedGridPoint> = input.map { row -> row.toList().map { FencedGridPoint(it, false) } }
        map.forEachPoint { visitableGridPoint, coordinates ->
            if (!visitableGridPoint.visited) {
                partTwoFencing(visitableGridPoint.value, coordinates, map)
            }
        }
        map.forEachPoint { point, _ -> point.visited = false }
        return map.sumOfPoints { visitableGridPoint, coordinates ->
            if (!visitableGridPoint.visited) {
                val (sides, area) = partTwoPricing(visitableGridPoint.value, coordinates, map)
                sides * area
            } else {
                0
            }
        }
    }

    val test1 = readFile("day12/test1.txt")
    val test2 = readFile("day12/test2.txt")
    val test3 = readFile("day12/test3.txt")
    assert(partOne(test1) == 140L)
    assert(partOne(test2) == 772L)
    assert(partOne(test3) == 1930L)

    assert(partTwo(test1) == 80L)
    assert(partTwo(test2) == 436L)
    val test4 = readFile("day12/test4.txt")
    val test5 = readFile("day12/test5.txt")
    assert(partTwo(test4) == 236L)
    assert(partTwo(test5) == 368L)
    assert(partTwo(test3) == 1206L)

    val input = readInput("day12")
    println(partOne(input))
    println(partTwo(input))
}