fun main() {

    class Rule(val first: String, val second: String) {
        fun test(update: String): Boolean {
            val firstPosition = update.indexOf(first)
            val secondPosition = update.indexOf(second)
            return firstPosition == -1 || secondPosition == -1 || firstPosition < secondPosition
        }
    }

    fun partOne(input: List<String>): Int {
        val (rules, updates) = input.filterNot { it.isEmpty() }.partition { it.contains("|") }
        val rulebook = rules.map {
            val (f, s) = it.split("|")
            Rule(f, s)
        }

        return updates.sumOf { update ->
            if (rulebook.all { it.test(update) }) {
                val parts = update.split(",")
                parts[parts.size / 2].toInt()
            } else {
                0
            }
        }
    }


    fun partTwo(input: List<String>): Int {
        val (rules, updates) = input.filterNot { it.isEmpty() }.partition { it.contains("|") }
        val rulebook = rules.map {
            val (f, s) = it.split("|")
            Rule(f, s)
        }

        val (valid, invalid) = updates.partition { update -> rulebook.all { it.test(update) } }

        val part1 = valid.sumOf { update ->
            val parts = update.split(",")
            parts[parts.size / 2].toInt()
        }
        println("Part one: $part1")

        val part2 = invalid.sumOf { update ->
            val parts = update.split(",")
            val sortedParts = parts.sortedWith { o1, o2 ->
                if (rulebook.any { it.first == o1 && it.second == o2 }) -1
                else if (rulebook.any { it.first == o1 && it.second == o2 }) 1
                else 0
            }
            sortedParts[parts.size / 2].toInt()
        }
        println("Part two: $part2")
        return part2
    }

    val test = readTest("day05")
//    assert(partOne(test) == 143)
    assert(partTwo(test) == 123)
    val input = readInput("day05")
//    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}