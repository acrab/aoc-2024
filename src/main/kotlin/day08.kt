import kotlin.math.abs

fun main() {

    fun partOne(input: List<String>): Int {
        val maxX = input[0].length
        val maxY = input.size
        val frequencies = mutableMapOf<Char, MutableList<Point>>()
        input.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c != '.') {
                    frequencies.upsert(c, Point(x, y))
                }
            }
        }

        return frequencies.entries.flatMap { (_, antennas) ->
            antennas.flatMap { source ->
                antennas.mapNotNull { target ->
                    if (target == source) return@mapNotNull null
                    val xDelta = source.first - target.first
                    val nodeX = if (xDelta < 0) {
                        // it's right of us
                        if (target.first + abs(xDelta) >= maxX) return@mapNotNull null
                        target.first + abs(xDelta)
                    } else {
                        // it's left of us
                        if (target.first - abs(xDelta) < 0) return@mapNotNull null
                        target.first - abs(xDelta)
                    }

                    val yDelta = source.second - target.second
                    val nodeY = if (yDelta < 0) {
                        // it's below us
                        if (target.second + abs(yDelta) >= maxY) return@mapNotNull null
                        target.second + abs(yDelta)
                    } else {
                        // it's above us
                        if (target.second - abs(yDelta) < 0) return@mapNotNull null
                        target.second - abs(yDelta)
                    }
                    return@mapNotNull Point(nodeX, nodeY)
                }
            }
        }.toSet().size
    }

    fun partTwo(input: List<String>): Int {
        val maxX = input[0].length
        val maxY = input.size
        val frequencies = mutableMapOf<Char, MutableList<Point>>()
        input.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c != '.') {
                    frequencies.upsert(c, Point(x, y))
                }
            }
        }

        return frequencies
            .entries
            .flatMap { (_, antennas) ->
                antennas.flatMap { source ->
                    antennas.flatMap { target ->
                        if (target == source) {
                            // Don't calculate against self
                            emptyList()
                        } else {
                            // calculate slope
                            val xDelta = target.first - source.first
                            val yDelta = target.second - source.second
                            // Start from the source node
                            var x = source.first
                            var y = source.second
                            // compute nodes
                            buildList {
                                while (x in 0..<maxX && y in 0..<maxY) {
                                    add(Point(x, y))
                                    x += xDelta
                                    y += yDelta
                                }
                            }
                        }
                    }
                }
            }
            .toSet()
            .size
    }

    val test = readTest("day08")
    val p1Test = partOne(test)
    assert(p1Test == 14) { "Expected 14, got $p1Test" }
    assert(partTwo(test) == 34)
    println("Tests passed")
    val input = readInput("day08")
    println(partOne(input))
    println(partTwo(input))
}