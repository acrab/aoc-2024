fun main() {

    fun findTarget(runningTotal: Long, values: List<Long>, goal: Long): Long? {
        if (values.isEmpty()) {
            return if (runningTotal == goal) goal
            else null
        }
        return findTarget(runningTotal + values[0], values.drop(1), goal)
            ?: findTarget(runningTotal * values[0], values.drop(1), goal)
    }

    fun partOne(input: List<String>): Long {
        return input.mapNotNull { line ->
            val (sumString, valueString) = line.split(": ")
            val targetSum = sumString.toLong()
            val values = valueString.split(" ").map { it.toLong() }
            findTarget(0, values, targetSum)
        }.sum()
    }

    fun concat(first: Long, second: Long): Long = buildString {
        append(first)
        append(second)
    }.toLong()

    fun findTargetPartTwo(runningTotal: Long, values: List<Long>, goal: Long): Long? {
        if (values.isEmpty()) {
            return if (runningTotal == goal) goal
            else null
        }
        return findTargetPartTwo(runningTotal + values[0], values.drop(1), goal)
            ?: findTargetPartTwo(runningTotal * values[0], values.drop(1), goal)
            ?: findTargetPartTwo(concat(runningTotal, values[0]), values.drop(1), goal)
    }

    fun partTwo(input: List<String>): Long {
        return input.mapNotNull { line ->
            val (sumString, valueString) = line.split(": ")
            val targetSum = sumString.toLong()
            val values = valueString.split(" ").map { it.toLong() }
            findTargetPartTwo(0, values, targetSum)
        }.sum()
    }

    val test = readTest("day07")
    assert(partOne(test) == 3749L)
    assert(partTwo(test) == 11387L)
    val input = readInput("day07")
    println(partOne(input))
    println(partTwo(input))
}