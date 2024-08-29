package io.github.devdamiani.gradle.liferayMinimal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import io.github.devdamiani.gradle.liferayMinimal.utils.Command
import org.gradle.api.tasks.Input

abstract class DockerComposeStopTask : DefaultTask() {

    @get:Input
    var profiles: List<String> = mutableListOf()

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        cmd.execute(
                project,
                listOf("docker", "compose", "-f", dockerComposeFile, "stop"),
                profiles
        )
    }
}
