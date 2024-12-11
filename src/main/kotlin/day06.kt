fun main() {

    data class VisitableGridPoint(
        val value: Char,
        val visitedFromDirections: MutableList<Direction> = mutableListOf()
    ) {
        val isStartPoint = value == '^'
        val isWall = value == '#'
        val isFloor = !isWall

        val visited: Boolean
            get() = visitedFromDirections.any()

        override fun toString(): String =
            if (visitedFromDirections.any()) "X"
            else if (isWall) "#"
            else " "

    }

    fun Grid<VisitableGridPoint>.print() {
        println(joinToString(separator = "\n") { it.joinToString(separator = "") })
        println()
    }

    fun parseGrid(input: List<String>): Grid<VisitableGridPoint> =
        input.map { row -> row.toList().map { VisitableGridPoint(it) } }

    fun walkLeft(startPosition: Point, map: Grid<VisitableGridPoint>): Point? {
        var (x, y) = startPosition
        while (x >= 0 && map.get(x, y).isFloor) {
            map.get(x, y).visitedFromDirections.add(Direction.LEFT)
            x--
        }
        return if (x < 0) null else Point(x + 1, y)
    }

    fun walkRight(startPosition: Point, map: Grid<VisitableGridPoint>): Point? {
        var (x, y) = startPosition
        val maxX = map[0].size - 1
        while (x <= maxX && map.get(x, y).isFloor) {
            map.get(x, y).visitedFromDirections.add(Direction.RIGHT)
            x++
        }
        return if (x > maxX) null else Point(x - 1, y)
    }

    fun walkUp(startPosition: Point, map: Grid<VisitableGridPoint>): Point? {
        var (x, y) = startPosition
        while (y >= 0 && map.get(x, y).isFloor) {
            map.get(x, y).visitedFromDirections.add(Direction.UP)
            y--
        }
        return if (y < 0) null else Point(x, y + 1)
    }

    fun walkDown(startPosition: Point, map: Grid<VisitableGridPoint>): Point? {
        var (x, y) = startPosition
        val maxY = map.size - 1
        while (y <= maxY && map.get(x, y).isFloor) {
            map.get(x, y).visitedFromDirections.add(Direction.DOWN)
            y++
        }
        return if (y > maxY) null else Point(x, y - 1)
    }

    fun walk(direction: Direction, startPosition: Point, map: Grid<VisitableGridPoint>): Point? =
        when (direction) {
            Direction.UP -> walkUp(startPosition, map)
            Direction.LEFT -> walkLeft(startPosition, map)
            Direction.DOWN -> walkDown(startPosition, map)
            Direction.RIGHT -> walkRight(startPosition, map)
        }


    fun partOne(input: List<String>): Int {
        val grid = parseGrid(input)
        var currentPoint: Point? = grid.positionOfFirst { it.isStartPoint }
        var currentDirection = Direction.UP
        while (currentPoint != null) {
            currentPoint = walk(currentDirection, currentPoint, grid)
            currentDirection = currentDirection.turnClockwise()
        }
        grid.print()
        return grid.countAll { it.visited }
    }


    fun partTwo(input: List<String>): Int {
        return 0
    }


    val test = readTest("day06")
    assert(partOne(test) == 41)
    assert(partTwo(test) == 6)
    val input = readInput("day06")
    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}