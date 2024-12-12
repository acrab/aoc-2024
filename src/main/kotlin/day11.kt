fun main() {

    fun performRepetitionsWithList(input: String, repetitions: Int): Int {
        var stones = input.split(" ").map { it.toLong() }
        val startTime = System.currentTimeMillis()
        repeat(repetitions) {
            println("${System.currentTimeMillis() - startTime}: on step $it with ${stones.size} stones")
            val s = buildList {

                stones.forEach { stone ->
                    if (stone == 0L) {
                        add(1L)
                    } else {
                        val str = stone.toString()
                        if (str.length % 2 == 0) {
                            addAll(str.chunked(str.length / 2) { it.toString().toLong() })
                        } else {
                            add(stone * 2024L)
                        }
                    }
                }
            }
            stones = s
        }
        return stones.size
    }

    fun performRepetitionsWithMap(input: String, repetitions: Int): Long {
        var stones = input.split(" ").map { it.toLong() }.associateWith { 1L }
        repeat(repetitions) {
            val s = buildMap {
                stones.forEach { (value, count) ->
                    if (value == 0L) {
                        addOrSet(1L, count)
                    } else {
                        val str = value.toString()
                        if (str.length % 2 == 0) {
                            val (l, r) = str.chunked(str.length / 2) { it.toString().toLong() }
                            addOrSet(l, count)
                            addOrSet(r, count)
                        } else {
                            addOrSet(value * 2024L, count)
                        }
                    }
                }
            }
            stones = s
        }
        return stones.sumOf { it.value }
    }

    fun partOne(input: String): Int {
        return performRepetitionsWithList(input, 25)
    }

    fun partTwo(input: String): Long {
        return performRepetitionsWithMap(input, 75)
    }

    val test = readTest("day11")[0]
    val partOne = partOne(test)
    assert(partOne == 55312) { "Expected 55312, got $partOne" }
    val input = readInput("day11")[0]
    println(partOne(input))
    println(partTwo(input))

}
