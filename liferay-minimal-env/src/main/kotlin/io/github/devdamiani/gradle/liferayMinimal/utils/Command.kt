package io.github.devdamiani.gradle.liferayMinimal.utils

import org.gradle.api.Project
import java.io.File
import java.lang.RuntimeException

open class Command {

    fun execute(project: Project, commandLineContent: List<String>, profiles: List<String> = listOf(), setWorkingDir: String? = null) {

        val execResult = project.exec { execSpec ->

            execSpec.workingDir = setWorkingDir?.let { File(it) } ?: project.rootDir

            if (profiles.isNotEmpty()) {
                execSpec.environment("COMPOSE_PROFILES", profiles.joinToString(" "))
            }

            execSpec.commandLine = commandLineContent
        }

        if (execResult.exitValue != 0) {
            throw RuntimeException("Command failed: ${commandLineContent.joinToString(" ")}")
        }
    }
}
