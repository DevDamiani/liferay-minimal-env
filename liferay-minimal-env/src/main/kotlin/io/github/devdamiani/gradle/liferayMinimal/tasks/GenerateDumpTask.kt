package io.github.devdamiani.gradle.liferayMinimal.tasks

import io.github.devdamiani.gradle.liferayMinimal.utils.Command
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.text.SimpleDateFormat
import java.util.Date

abstract class GenerateDumpTask : DefaultTask() {

    @TaskAction
    fun init() {
        val dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        val workingDir = project.rootDir

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyyMMdd:HH:mm")
        val formattedDate = dateFormat.format(currentDate)

        val cmd = Command()

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "exec", "database", "bash", "-c",
                "mysqldump -u root -p\$MYSQL_ROOT_PASSWORD --databases lportal > 01-liferay-lite-dump-$formattedDate.sql"
        ))

        cmd.execute(project, listOf(
                "find", "$workingDir/docker/database/dump", "-type", "f", "-name", "*.sql", "-exec", "rm", "-f", "{}", ";"
        ))

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "cp", "database:/01-liferay-lite-dump-$formattedDate.sql", "$workingDir/docker/database/dump/"
        ))

        cmd.execute(project, listOf(
                "rm", "-rf", "$workingDir/configs/local/data/document_library"
        ))

        cmd.execute(project, listOf(
                "docker", "compose", "-f", dockerComposeFile, "cp", "liferay:/opt/liferay/data/document_library", "$workingDir/configs/local/data/document_library"
        ))
    }
}
