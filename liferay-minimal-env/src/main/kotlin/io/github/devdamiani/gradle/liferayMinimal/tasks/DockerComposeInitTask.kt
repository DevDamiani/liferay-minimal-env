package io.github.devdamiani.gradle.liferayMinimal.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Input
import java.io.File

import io.github.devdamiani.gradle.liferayMinimal.utils.Command

abstract class DockerComposeInitTask : DefaultTask() {

    @get:Input
    var profiles: List<String> = mutableListOf()

    @TaskAction
    fun init() {


        if (!hasDockerComposeFile()) {
            applyDockerResources()
        }

        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        cmd.execute(project, listOf("docker", "compose", "-f", dockerComposeFile, "down", "-v"), profiles)
        cmd.execute(project, listOf("docker", "compose", "-f", dockerComposeFile, "up", "-d", "--build", "-V", "--remove-orphans"), profiles)
    }

    private fun applyDockerResources() {

        project.logger.quiet("[DockerComposeInitTask] Creating Docker compose content...")

        val liferayVersion = checkLiferayVersion()

        project.logger.quiet("[DockerComposeInitTask] version: $liferayVersion")

        val resourceFileName = "$liferayVersion.zip"

        val outputDir = project.file("${project.projectDir}/")

        val zipOutputFile = project.file("${project.projectDir}/.gradle")
        val zipFile = project.file("${project.projectDir}/.gradle/$resourceFileName")

        extractResourceFile(resourceFileName, zipOutputFile)

        extractZipFile(zipFile, outputDir)

        deleteFile(zipFile)
    }

    private fun extractResourceFile(resourceFileName: String, outputLocation: File) {
        val inputStream = this::class.java.getResourceAsStream("/versions/$resourceFileName")

        if (inputStream != null) {
            val destinationFile = File(outputLocation, resourceFileName)

            outputLocation.mkdirs()
            destinationFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }

            inputStream.close()
        }
    }

    private fun extractZipFile(fileLocation: File, outputLocation: File) {
        if (!fileLocation.exists()) {
            return
        }

        project.copy {
            it.from(project.zipTree(fileLocation))
            it.into(outputLocation)
        }
    }

    private fun deleteFile(file: File) {
        if (file.exists()) {
            if (!file.delete()) {
                println("Failed to delete file: $file")
            }
        }
    }

    private fun hasDockerComposeFile(): Boolean {

        val files = project.fileTree(project.projectDir).matching { it.exclude { details -> details.isDirectory } }.files
        val containsModuleFiles = files.find { file ->
            file.name.contains("compose.yaml") ||
                    file.name.contains("compose.yml") ||
                    file.name.contains("docker-compose.yml") ||
                    file.name.contains("docker-compose.yaml")
        }

        return containsModuleFiles != null
    }

    private fun checkLiferayVersion(): String? {

        val liferayVersion = project.property("liferay.workspace.product").toString()

        val liferay74 = listOf("dxp-", "7.4")

        return when {
            liferayVersion.contains("7.3") -> "7.3"
            liferay74.any{ liferayVersion.contains(it) } -> "7.4"
            else -> "7.4"
        }
    }
}
