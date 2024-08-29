package io.github.devdamiani.liferayMinimal.tasks

import io.github.devdamiani.liferayMinimal.utils.Command
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.text.SimpleDateFormat
import java.util.Date

class GenerateDumpMySQL : DefaultTask() {

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val workingDir = project.rootDir

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyyMMdd:HH:mm")
        val formattedDate = dateFormat.format(currentDate)

        val cmd = Command()

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "exec", "mysql", "bash", "-c",
                "mysqldump -u root -prGC9rmmG --databases lportal > 01-liferay-lite-dump-$formattedDate.sql"
        ))

        cmd.execute(project, listOf(
                "find", "$workingDir/docker/mysql/dump", "-type", "f", "-name", "*.sql", "-exec", "rm", "-f", "{}", ";"
        ))

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "cp", "mysql:/01-liferay-lite-dump-$formattedDate.sql", "$workingDir/docker/mysql/dump/"
        ))

        cmd.execute(project, listOf(
                "rm", "-rf", "$workingDir/configs/local/data/document_library"
        ))

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "cp", "liferay:/opt/liferay/data/document_library", "$workingDir/configs/local/data/document_library"
        ))
    }
}
