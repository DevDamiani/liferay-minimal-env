package io.github.devdamiani.liferayMinimal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import io.github.devdamiani.liferayMinimal.utils.Command
import java.io.File

open class DockerComposeDeploy : DefaultTask() {

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        val clientExtensionFile = project.file("client-extension.yaml")
        val deployDir = if (clientExtensionFile.exists()) {
            "${project.projectDir}/dist"
        } else {
            "${project.projectDir}/build/libs"
        }

        project.fileTree(File(deployDir)).visit { details ->
            cmd.execute(
                    project,
                    listOf("docker", "compose", "-f", dockerComposeFile, "cp", details.file.path, "liferay:/opt/liferay/deploy/")
            )
        }
    }
}
