fun main() {

    fun handleCommand(command: String, currentDir: CommandDir): CommandDir {
        if (command.contains("$ ls"))
            return currentDir

        val parsedCommand: String = command.split(" ").last()

        if (parsedCommand == "/"){
            var returnDir = currentDir
            do {
                returnDir = returnDir.getParent()
            }while (returnDir.getParent() != returnDir)
            return returnDir
        }

        if (parsedCommand == "..")
            return currentDir.getParent()

        return currentDir.dirs.first { it.name == parsedCommand }
    }

    fun handleLsDir(list: String, currentDir: CommandDir){
        currentDir.dirs.add(CommandDir(name = list.split(" ").last(), parentDir = currentDir))
    }

    fun handleLsFile(list: String, currentDir: CommandDir){
        val listFile = list.split(" ")
        currentDir.files.add(CommandFile(name = listFile.last(), size = listFile.first().toInt()))
    }

    fun handleLs(list: String, currentDir: CommandDir): CommandDir {
        when(list.startsWith("dir")){
            true -> handleLsDir(list, currentDir)
            false -> handleLsFile(list, currentDir)
        }
        return currentDir
    }

    fun parseInput(input: List<String>): CommandDir {
        val root = CommandDir("/")

        var currentDir = root
        for (command in input) {
            currentDir = when (command.startsWith("$")) {
                true -> handleCommand(command, currentDir)
                false -> handleLs(command, currentDir)
            }
        }

        return root
    }

    fun part1(commandDir: CommandDir, currentSize: Int): Int {

        val newSize = if (commandDir.getSize() <= 100_000) commandDir.getSize() else 0

        return newSize +
                commandDir.dirs.sumOf{ part1(it,currentSize + newSize) }
    }

    fun part2(commandDir: CommandDir, currentMin: Int, sizeNeeded: Int): Int{
        val newSize = if (commandDir.getSize() >= sizeNeeded) commandDir.getSize() else return currentMin

        if (commandDir.dirs.size == 0)
            return newSize

        return if (newSize <= currentMin ) commandDir.dirs.minOf { part2(it, newSize, sizeNeeded) } else currentMin
    }

    val input = readInput("Day07")
    val rootDir = parseInput(input)
    println(part1(rootDir, 0))
    println(part2(rootDir, rootDir.getSize(), 30_000_000 - 70_000_000 + rootDir.getSize()))
}

data class CommandFile(val name: String, val size: Int)

data class CommandDir(
    val name: String,
    val dirs: MutableList<CommandDir> = mutableListOf(),
    val files: MutableList<CommandFile> = mutableListOf(),
    val parentDir: CommandDir? = null
) {
    fun getParent(): CommandDir {
        return this.parentDir ?: this
    }

    fun getSize(): Int {
        return files.sumOf { it.size } + dirs.sumOf { it.getSize() }
    }

    override fun toString(): String {
        return "CommandDir(name='$name')"
    }
}
