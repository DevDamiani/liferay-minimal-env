package io.github.devdamiani.liferayMinimal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import io.github.devdamiani.liferayMinimal.utils.Command

open class DockerComposeDownTask : DefaultTask() {

    @get:Input
    var profiles: List<String> = mutableListOf()

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        cmd.execute(
                project,
                listOf("docker", "compose", "-f", dockerComposeFile, "down", "-v"),
                profiles
        )
    }
}
