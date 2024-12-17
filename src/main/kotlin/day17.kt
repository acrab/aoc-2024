import kotlin.math.pow

fun main() {

    fun compute(initialA: Long, initialB: Long, initialC: Long, program: List<Long>): String {
        var a: Long = initialA
        var b: Long = initialB
        var c: Long = initialC

        fun combo(operand: Long): Long = when (operand) {
            in 0L..3L -> operand
            4L -> a
            5L -> b
            6L -> c
            else -> error("Unexpected operand $operand")
        }

        val output = mutableListOf<Long>()

        var programCounter = 0
        while (programCounter < program.size) {
            val operation = program[programCounter]
            val operand = program[programCounter + 1]
            when (operation) {
                0L -> { // adv
                    a /= 2.0.pow(combo(operand).toDouble()).toInt()
                    programCounter += 2
                }

                1L -> { // bxl
                    b = b.xor(operand)
                    programCounter += 2
                }

                2L -> { // bst
                    b = combo(operand) % 8
                    programCounter += 2
                }

                3L -> { // jnz
                    if (a == 0L) {
                        programCounter += 2
                    } else {
                        programCounter = operand.toInt()
                    }
                }

                4L -> { // bxc
                    b = b.xor(c)
                    programCounter += 2
                }

                5L -> { // out
                    output += combo(operand) % 8
                    programCounter += 2
                }

                6L -> { //bdv
                    b = a / 2.0.pow(combo(operand).toInt()).toLong()
                    programCounter += 2
                }

                7L -> { //cdv
                    c = a / 2.0.pow(combo(operand).toInt()).toLong()
                    programCounter += 2
                }
            }
        }
        println("$output")
        return output.joinToString(",")
    }


    fun partOne(input: List<String>): String {
        val a: Long = input[0].removePrefix("Register A: ").toLong()
        val b: Long = input[1].removePrefix("Register B: ").toLong()
        val c: Long = input[2].removePrefix("Register C: ").toLong()

        val program = input[4].removePrefix("Program: ")
            .split(",")
            .map { it.toLong() }

        return compute(a, b, c, program)
    }

    fun partTwo(input: List<String>): Long {

        val program = input[4].removePrefix("Program: ")
            .split(",")
            .map { it.toLong() }

        /**
         * This makes some assumptions that are true for my input, but may not be true for all inputs!
         * Specifically, this assumes that the last two operations modify "A" and trigger the next loop.
         * In this case, we're doing both of those externally to the program.
         */
        fun testProgram(a: Long): Long {
            return compute(a, 0, 0, program.dropLast(2)).toLong()
        }


        /**
         * Start at the end of the program, working towards the start.
         * On the final iteration, A must end up as 0, and must produce the last character of the program as it's output
         * For each value that meets those critera, find the values that produce that result, and output the previous program character
         * and so on, until all characters have been produced.
         *
         * Assumption: A is divided by 8 on each iteration. This is true in my input, but may not be true in all!
         */
        var possibles: List<Long> = listOf(0)
        for (i in program.size - 1 downTo 0) {
            possibles = possibles.flatMap { possibleResult ->
                val a = possibleResult * 8
                (a..a + 7).filter { testProgram(it) == program[i] }
            }
        }

        return possibles.min()
    }


    val test = readTest("day17")
    assert(partOne(test) == "4,6,3,5,6,3,5,2,1,0")
    val input = readInput("day17")
    println(partOne(input))
    println(partTwo(input))
}