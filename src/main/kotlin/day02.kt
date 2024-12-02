fun main() {

    fun basicSafetyCheck(nums: List<Int>): Boolean {
        val windows = nums.windowed(2)
        return windows.all { (l, r) -> l - r in 1..3 } || windows.all { (l, r) -> r - l in 1..3 }
    }

    fun partOne(input: List<String>): Int {
        return input.count { report ->
            basicSafetyCheck(report.split(" ").map { it.toInt() })
        }
    }


    fun partTwo(input: List<String>): Int {
        return input.count { report ->
            val nums = report.split(" ").map { it.toInt() }

            basicSafetyCheck(nums) || nums.indices.any {
                basicSafetyCheck(nums.subList(0, it) + nums.subList(it + 1, nums.size))
            }
        }
    }

    val test = readTest("day02")
    assert(partOne(test) == 2)
    assert(partTwo(test) == 4)
    val input = readInput("day02")
    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}