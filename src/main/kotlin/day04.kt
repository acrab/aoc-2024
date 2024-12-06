typealias Grid<T> = List<List<T>>

fun main() {

    // x is horizontal
    // y is vertical
    // 0,0 is top left

    fun searchUp(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y < 3 -> false
            map[y - 1][x] != 'M' -> false
            map[y - 2][x] != 'A' -> false
            map[y - 3][x] != 'S' -> false
            else -> true
        }
    }

    fun searchDown(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y > map.size - 4 -> false
            map[y + 1][x] != 'M' -> false
            map[y + 2][x] != 'A' -> false
            map[y + 3][x] != 'S' -> false
            else -> true
        }
    }

    fun searchLeft(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            x < 3 -> false
            map[y][x - 1] != 'M' -> false
            map[y][x - 2] != 'A' -> false
            map[y][x - 3] != 'S' -> false
            else -> true
        }
    }

    fun searchRight(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            x > map[0].size - 4 -> false
            map[y][x + 1] != 'M' -> false
            map[y][x + 2] != 'A' -> false
            map[y][x + 3] != 'S' -> false
            else -> true
        }
    }

    fun searchUpLeft(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y < 3 -> false
            x < 3 -> false
            map[y - 1][x - 1] != 'M' -> false
            map[y - 2][x - 2] != 'A' -> false
            map[y - 3][x - 3] != 'S' -> false
            else -> true
        }
    }

    fun searchDownLeft(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y > map.size - 4 -> false
            x < 3 -> false
            map[y + 1][x - 1] != 'M' -> false
            map[y + 2][x - 2] != 'A' -> false
            map[y + 3][x - 3] != 'S' -> false
            else -> true
        }
    }

    fun searchUpRight(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y < 3 -> false
            x > map[0].size - 4 -> false
            map[y - 1][x + 1] != 'M' -> false
            map[y - 2][x + 2] != 'A' -> false
            map[y - 3][x + 3] != 'S' -> false
            else -> true
        }
    }

    fun searchDownRight(x: Int, y: Int, map: Grid<Char>): Boolean {
        return when {
            y > map.size - 4 -> false
            x > map[0].size - 4 -> false
            map[y + 1][x + 1] != 'M' -> false
            map[y + 2][x + 2] != 'A' -> false
            map[y + 3][x + 3] != 'S' -> false
            else -> true
        }
    }

    val checks = listOf(
        ::searchUp,
        ::searchDown,
        ::searchLeft,
        ::searchRight,
        ::searchUpLeft,
        ::searchUpRight,
        ::searchDownLeft,
        ::searchDownRight
    )


    fun partOne(input: List<String>): Int {
        var total = 0
        val map = input.map { it.toList() }
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'X') {
                    total += checks.count { it(x, y, map) }
                }
            }
        }
        return total
    }

    fun `x-mas check`(x: Int, y: Int, map: Grid<Char>): Boolean {
        if (x == 0 || y == 0 || x >= map[0].size-1 || y >= map.size-1) return false
        if (map[y][x] != 'A') return false
        // check forward diagonal
        return (
                    (map[y - 1][x - 1] == 'M' && map[y + 1][x + 1] == 'S') ||
                    (map[y - 1][x - 1] == 'S' && map[y + 1][x + 1] == 'M')
                ) && (
                    (map[y - 1][x + 1] == 'M' && map[y + 1][x - 1] == 'S') ||
                    (map[y - 1][x + 1] == 'S' && map[y + 1][x - 1] == 'M')
                )
    }

    fun partTwo(input: List<String>): Int {
        var total = 0
        val map = input.map { it.toList() }
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (`x-mas check`(x, y, map)) {
                    total++
                }
            }
        }
        return total
    }

    val test = readTest("day04")
    assert(partOne(test) == 18)
    assert(partTwo(test) == 9)
    val input = readInput("day04")
    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}