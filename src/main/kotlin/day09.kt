fun main() {

    fun String.parseDisk(): List<Int> {
        var isFile = true
        var fileId = 0
        return flatMap {
            val blockLength = it.digitToInt()
            if (isFile) {
                List(blockLength) { fileId }.also {
                    fileId++
                    isFile = false
                }
            } else {
                List(blockLength) { -1 }.also {
                    isFile = true
                }
            }
        }
    }

    fun MutableList<Int>.calculateChecksum() =
        sumOfIndexed { value, index: Long -> if (value > 0) value * index else 0 }

    fun partOne(input: String): Long {
        val originalDisk = input.parseDisk()

        val newDisk = MutableList(originalDisk.size) { -1 }

        var startPointer = 0
        var endPointer = originalDisk.size - 1
        while (startPointer <= endPointer) {
            // copy old disk directly until we reach a gap
            while (originalDisk[startPointer] != -1 && startPointer <= endPointer) {
                newDisk[startPointer] = originalDisk[startPointer]
                startPointer++
            }

            // copy files from end of old disk, jumping over gaps, until the space is filled
            while (originalDisk[startPointer] == -1 && startPointer <= endPointer) {
                while (originalDisk[endPointer] == -1 && startPointer <= endPointer) {
                    endPointer--
                }
                newDisk[startPointer] = originalDisk[endPointer]
                startPointer++
                endPointer--
            }
        }
        return newDisk.calculateChecksum()
    }

    data class DiskEntry(var length: Long, val isFile: Boolean, val id: Int, var startPoint: Long) {
        override fun toString(): String =
            if (!isFile) ".".repeat(length.toInt())
            else "$id".repeat(length.toInt())
    }

    fun String.parseDiskEntries(): MutableList<DiskEntry> {
        var isFile = true
        var fileId = 0
        var diskPosition = 0L
        return MutableList(length) {
            val length = get(it).digitToInt().toLong()
            DiskEntry(length, isFile, fileId, diskPosition)
                .also {
                    if (isFile) {
                        fileId++
                    }
                    isFile = !isFile
                    diskPosition += length
                }
        }
    }

    fun calculateDiskEntriesChecksum(disk: MutableList<DiskEntry>) = disk.filter { it.isFile }.sumOf {
        (it.startPoint..<(it.startPoint + it.length)).sum() * it.id
    }

    fun partTwo(input: String): Long {
        val disk = input.parseDiskEntries()

        val reversedFiles = disk.reversed().filter { it.isFile }
        reversedFiles.forEach { file ->
            val space = disk.firstOrNull { !it.isFile && it.length >= file.length && it.startPoint < file.startPoint }
            if (space != null) {
                file.startPoint = space.startPoint
                space.startPoint += file.length
                space.length -= file.length
            }
        }

        return calculateDiskEntriesChecksum(disk)
    }

    val test = readTest("day09")
    assert(partOne(test[0]) == 1928L)
    assert(partTwo(test[0]) == 2858L)
    val input = readInput("day09")
    println(partOne(input[0]))
    println(partTwo(input[0]))
}