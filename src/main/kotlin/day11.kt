import java.lang.Long.parseLong

fun main() {

    fun performRepetitionsWithList(input: String, repetitions: Int): Int {
        var stones = input.toLongList()
        repeat(repetitions) {
            stones = buildList {
                stones.forEach { stone ->
                    if (stone == 0L) {
                        add(1L)
                    } else {
                        val str = stone.toString()
                        if (str.length % 2 == 0) {
                            addAll(str.chunked(str.length / 2) { parseLong(it, 0, it.length, 10) })
                        } else {
                            add(stone * 2024L)
                        }
                    }
                }
            }
        }
        return stones.size
    }

    fun performRepetitionsWithMap(input: String, repetitions: Int): Long {
        var stones = input.toLongList().associateWith { 1L }
        repeat(repetitions) {
            stones = buildMap {
                stones.forEach { (value, count) ->
                    if (value == 0L) {
                        addOrSet(1L, count)
                    } else {
                        val str = value.toString()
                        if (str.length % 2 == 0) {
                            val (l, r) = str.chunked(str.length / 2) { parseLong(it, 0, it.length, 10) }
                            addOrSet(l, count)
                            addOrSet(r, count)
                        } else {
                            addOrSet(value * 2024L, count)
                        }
                    }
                }
            }
        }
        return stones.sumOf { it.value }
    }

    fun partOne(input: String): Int {
        return performRepetitionsWithList(input, 25)
    }

    fun partOneMap(input: String): Long {
        return performRepetitionsWithMap(input, 25)
    }

    fun partTwo(input: String): Long {
        return performRepetitionsWithMap(input, 75)
    }

    val test = readTest("day11")[0]
    val partOneTest = partOne(test)
    assert(partOneTest == 55312) { "Expected 55312, got $partOneTest" }
    val input = readInput("day11")[0]

    val p1StartTime = System.nanoTime()
    val partOne = partOne(input)
    val p1Duration = System.nanoTime() - p1StartTime
    println(partOne)
    println("Finished in ${p1Duration / 1_000_000.0} ms")

    val p1MapStartTime = System.nanoTime()
    val partOneMap = partOneMap(input)
    val p1MapDuration = System.nanoTime() - p1MapStartTime
    println(partOneMap)
    println("Finished in ${p1MapDuration / 1_000_000.0} ms")


    val startTime = System.nanoTime()
    val partTwo = partTwo(input)
    val duration = System.nanoTime() - startTime
    println(partTwo)
    println("Finished in  ${duration / 1_000_000.0} ms")

}
