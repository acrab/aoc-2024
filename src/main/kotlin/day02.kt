fun main() {

    fun List<Int>.basicSafetyCheck(): Boolean =
        with(windowed(2)) { all { (l, r) -> l - r in 1..3 } || all { (l, r) -> r - l in 1..3 } }

    fun partOne(input: List<String>): Int =
        input.count { it.toIntList().basicSafetyCheck() }

    fun partTwo(input: List<String>): Int =
        input.count { report ->
            with(report.toIntList()) {
                this.basicSafetyCheck() || indices.any { (subList(0, it) + subList(it + 1, size)).basicSafetyCheck() }
            }
        }

    val test = readTest("day02")
    assert(partOne(test) == 2)
    assert(partTwo(test) == 4)
    val input = readInput("day02")
    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}