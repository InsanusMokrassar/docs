import java.io.File

val templateEnding = ".template.md"
val singleArgumentRegex = Regex("^[\\w\\d]+$")
val splitterRegex = Regex("[ ]*=[ ]*")

val folder = File("./")

fun String.replaceVariables(variables: Map<String, String>): String {
    var currentLine = this
    variables.forEach { (k, v) ->
        currentLine = currentLine.replace("\${${k}}", v)
        if (k.matches(singleArgumentRegex)) {
            currentLine = currentLine.replace("\$${k}", v)
        }
    }
    return currentLine
}

fun generateFromTemplate(file: File) {
    val targetFile = File(folder, file.name.replace(templateEnding, ".md"))

    val variables = mutableMapOf<String, String>()
    var writeVariables = true
    var text = ""
    
    file.readLines().forEach { line ->
        when {
            writeVariables && line.startsWith("#") -> {
                writeVariables = false
            }
            writeVariables -> {
                val splitted = line.split(splitterRegex)
                if (splitted.size > 1) {
                    val k = splitted[0]
                    val v = splitted[1].replaceVariables(variables)
                    variables[k] = v
                }
                return@forEach
            }
        }
        text += line.let {
            line.replaceVariables(variables) + "\n"
        }
    }
    targetFile.writeText(text)
    println("${targetFile.name} has been recreated")
}

folder.listFiles().forEach { file ->
    if (file.name.endsWith(templateEnding)) {
        generateFromTemplate(file)
    }
}
