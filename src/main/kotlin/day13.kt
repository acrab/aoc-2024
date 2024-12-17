import kotlin.math.min

fun main() {

    val lineRegex = Regex(""".*: X.(\d*), Y.(\d*)""")

    fun parseLine(string: String): Pair<Long, Long> {
        val match = lineRegex.find(string)!!
        return match.groups[1]!!.value.toLong() to match.groups[2]!!.value.toLong()
    }

    fun partOne(input: List<String>): Long =
        input.filterNot { it.isEmpty() }
            .windowed(3, 3)
            .sumOf { (a, b, prize) ->
                val buttonA = parseLine(a)
                val buttonB = parseLine(b)
                val prizeLocation = parseLine(prize)
                val maxPresses = 100L
                // work out possible x values
                val maxAPresses = min(prizeLocation.first / buttonA.first, maxPresses)
                val maxBPresses = min(prizeLocation.first / buttonB.first, maxPresses)
                if (maxAPresses * buttonA.first + maxBPresses * buttonB.first < prizeLocation.first) {
                    // impossible to reach this with any valid number of presses
                    return@sumOf 0
                }

                val result = (0..maxAPresses).mapNotNull {
                    val matchingB = (prizeLocation.first - buttonA.first * it) / buttonB.first
                    if (buttonA.first * it + matchingB * buttonB.first == prizeLocation.first) {
                        it to matchingB
                    } else null
                }
                    // check if any are valid y values
                    .filter { buttonA.second * it.first + buttonB.second * it.second == prizeLocation.second }
                    // work out the cheapest
                    .minOfOrNull { it.first * 3 + it.second }

                result ?: 0
            }

    fun partTwo(input: List<String>): Long =
        input.filterNot { it.isEmpty() }
            .windowed(3, 3)
            .sumOf { (a, b, prize) ->
                val (a_x, a_y) = parseLine(a)
                val (b_x, b_y) = parseLine(b)
                val (p_x, p_y) = with(parseLine(prize)) { first + 10_000_000_000_000 to second + 10_000_000_000_000 }
                println("Testing $a $b -> $p_x, $p_y")
                val B = ((a_x * p_y) - (a_y * p_x)) / ((a_x * b_y) - (a_y * b_x))
                val A = (p_x - B * b_x) / a_x

                if ((((B * b_x) + (A * a_x)) == p_x) && (((B * b_y) + (A * a_y)) == p_y)) {
                    (A * 3) + B
                } else {
                    0
                }
            }

    val test = readTest("day13")
    assert(partOne(test) == 480L)
    assert(partTwo(test) != 0L)
    val input = readInput("day13")
    println(partOne(input))
    println(partTwo(input))
}