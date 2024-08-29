package io.github.devdamiani.liferayMinimal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import io.github.devdamiani.liferayMinimal.utils.Command
import org.gradle.api.tasks.Input

open class DockerComposeStopTask : DefaultTask() {

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
