package io.github.devdamiani.gradle.liferayMinimal.tasks

import io.github.devdamiani.gradle.liferayMinimal.utils.Command
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class DockerComposeUpTask : DefaultTask() {

    @get:Input
    var profiles: List<String> = mutableListOf()

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        cmd.execute(
            project,
            listOf("docker", "compose", "-f", dockerComposeFile, "up", "--build", "-d"),
            profiles
        )
    }
}
