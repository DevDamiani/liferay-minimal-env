package io.github.devdamiani.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.text.SimpleDateFormat

import io.github.devdamiani.gradle.utils.Command

class GenerateDumpMySQL extends DefaultTask {

    @TaskAction
    void init() {

        String dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        def workingDir = project.rootDir

        def currentDate = new Date()
        def dateFormat = new SimpleDateFormat("yyyyMMdd:HH:mm")

        def formattedDate = dateFormat.format(currentDate)

        def cmd = new Command()

        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'exec', 'mysql', 'bash', '-c', "mysqldump -u root -prGC9rmmG --databases lportal > 01-liferay-lite-dump-${formattedDate}.sql"])

        cmd.execute(project, ["find", "$workingDir/docker/mysql/dump", "-type", "f", "-name", "*.sql", "-exec", "rm", "-f", "{}", ";" ])

        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'cp', "mysql:/01-liferay-lite-dump-${formattedDate}.sql", "$workingDir/docker/mysql/dump/"])

        cmd.execute(project, ["rm", "-rf", "$workingDir/configs/local/data/document_library"])

        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'cp', "liferay:/opt/liferay/data/document_library", "$workingDir/configs/local/data/document_library"]) 
    }
}