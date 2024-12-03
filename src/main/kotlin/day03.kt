fun main() {

    fun partOne(input: List<String>): Int {
        val match = Regex("""mul\((\d*),(\d*)\)""")
        return input.sumOf { line ->
            match.findAll(line)
                .sumOf {
                    val x = it.groups[1]?.value?.toInt() ?: error("match with no groups!")
                    val y = it.groups[2]?.value?.toInt() ?: error("match with no groups!")
                    x * y
                }
        }

    }


    fun partTwo(input: List<String>): Int {
        var enabled = true
        val match = Regex("""mul\((\d*),(\d*)\)|do\(\)|don't\(\)""")
        return input.sumOf { line ->
            var sum = 0
            match.findAll(line)
                .forEach {
                    if (it.value == "do()") {
                        enabled = true
                    } else if (it.value == "don't()") {
                        enabled = false
                    } else if(enabled){
                        val x = it.groups[1]?.value?.toInt() ?: error("match with no groups!")
                        val y = it.groups[2]?.value?.toInt() ?: error("match with no groups!")
                        sum += x * y
                    }
                }
            sum
        }
    }

    val test = readTest("day03")
    assert(partOne(test) == 161)
    val testTwo = readFile("day03/test2.txt")
    assert(partTwo(testTwo) == 48)
    val input = readInput("day03")
    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}