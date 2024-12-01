import kotlin.math.abs

fun main() {

    fun partOne(input: List<String>): Int {
        val (left, right) = input.map { it.split("   ") }.unzip { it[0].toInt() to it[1].toInt() }
        return left.sorted().zip(right.sorted()) { l, r -> abs(l - r) }.sum()
    }

    fun partTwo(input: List<String>): Int {
        val (left, right) = input.map { it.split("   ") }.unzip { it[0].toInt() to it[1].toInt() }
        val sortedRightList = right.sorted()
        return left.sumOf { leftValue -> leftValue * sortedRightList.count { it == leftValue } }
    }

    val test = readTest("day01")
    assert(partOne(test) == 11)
    assert(partTwo(test) == 31)
    val input = readInput("day01")
    println(partOne(input))
    println(partTwo(input))
}