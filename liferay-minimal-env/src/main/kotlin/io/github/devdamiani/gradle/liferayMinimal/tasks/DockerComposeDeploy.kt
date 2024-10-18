package io.github.devdamiani.gradle.liferayMinimal.tasks

import io.github.devdamiani.gradle.liferayMinimal.extensions.DockerComposeDeployExtension
import io.github.devdamiani.gradle.liferayMinimal.utils.Command
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files


abstract class DockerComposeDeploy : DefaultTask() {

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val cmd = Command()

        val deployDir = getOutputBuildPathExtension() ?: getBuildPath()

        project.logger
            .info("[DockerComposeDeploy] deployDir: $deployDir")

        project.fileTree(File(deployDir)).visit { details ->
            cmd.execute(
                    project,
                    listOf("docker", "compose", "-f", dockerComposeFile, "cp", details.file.path, "liferay:/opt/liferay/deploy/")
            )
        }

        project.logger.quiet("[DockerComposeDeploy] Copy $deployDir to Liferay Container")
    }

    private fun getOutputBuildPathExtension(): String? {
        val deployExtension = project.extensions.findByType(DockerComposeDeployExtension::class.java)

        if (deployExtension != null) {

            if (!deployExtension.outputBuildPath.isNullOrEmpty()) {

                val outputBuildPath = "${project.projectDir}/${deployExtension.outputBuildPath}"

                project.logger
                    .info("[DockerComposeDeploy] outputBuildPath: $outputBuildPath")

                return outputBuildPath

            }
        }

        return null
    }

    private fun getBuildPath(): String {

        if(isCX(project)){
            return "${project.projectDir}/dist"
        }

        if(isOSGi(project)){
            val jarTask = project.tasks.findByName("jar")

            if (jarTask != null) {
                val jarFile = jarTask.outputs.files.singleFile
                project.logger.info("Output JAR file: ${jarFile.absolutePath}")
                return jarFile.absolutePath
            }
            return "${project.projectDir}/build/libs"
        }

        if(isTheme(project)){
            return "${project.projectDir}/dist"
        }

        return ""
    }

    private fun isCX(project: Project): Boolean {
        return project.file("client-extension.yaml").exists()
    }

    private fun isOSGi(project: Project): Boolean {
        return project.file("bnd.bnd").exists()
    }

    private fun isTheme(project: Project): Boolean {
        val gulpFile: File = project.file("gulpfile.js")

        if (!gulpFile.exists()) {
            return false
        }

        val gulpFileContent: String

        try {
            gulpFileContent = String(
                Files.readAllBytes(gulpFile.toPath()), StandardCharsets.UTF_8
            )
        } catch (ioException: IOException) {
            return false
        }

        return gulpFileContent.contains("liferay-theme-tasks")
    }
}
