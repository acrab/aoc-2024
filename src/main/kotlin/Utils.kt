/**
 * Reads lines from the given input txt file.
 */


private fun readFile(name: String) =
    object {}.javaClass.getResourceAsStream(name)?.bufferedReader()?.readLines() ?: error("Unable to read file $name!")

fun readInput(name: String) = readFile("$name/input.txt")
fun readTest(name: String) = readFile("$name/test.txt")

fun <O, L, R> List<O>.unzip(transform: (O) -> Pair<L, R>): Pair<List<L>, List<R>> {
    val left = mutableListOf<L>()
    val right = mutableListOf<R>()
    forEach {
        val (l, r) = transform(it)
        left.add(l)
        right.add(r)
    }
    return left to right
}