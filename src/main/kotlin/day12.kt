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
            } else if(!data.visited) {
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

    fun partTwoPricing(cropType: Char, startPoint: Point, map: Grid<VisitableGridPoint>): Pair<Long, Long> {
        val cardinals = map.cardinals(startPoint)
        var fenceCount = 4L - cardinals.size
        var areaCount = 1L
        map.get(startPoint).visited = true
        cardinals.map { it to map.get(it) }.forEach { (point, data) ->
            if (data.value != cropType) {
                fenceCount++
            } else if(!data.visited) {
                val (fences, area) = partTwoPricing(cropType, point, map)
                fenceCount += fences
                areaCount += area
            }
        }

        return fenceCount to areaCount
    }

    fun partTwo(input: List<String>): Long {
        val map: Grid<VisitableGridPoint> = input.map { row -> row.toList().map { VisitableGridPoint(it, false) } }
        return map.sumOfPoints { visitableGridPoint, coordinates ->
            if (visitableGridPoint.visited) 0L
            else {
                val (fences, area) = partTwoPricing(visitableGridPoint.value, coordinates, map)
                fences * area
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