package io.github.devdamiani.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.api.tasks.Input

import java.io.File
import java.nio.file.Files

import io.github.devdamiani.gradle.utils.Command

class DockerComposeDeploy extends DefaultTask {

    @TaskAction
    void init() {

        String dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        def cmd = new Command()

        if(project.file("client-extension.yaml").exists()) { 

            project.fileTree(dir: "${project.projectDir}/dist").visit { details ->
                cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'cp', details.file.path, "liferay:/opt/liferay/deploy/"])
            }
        } else {
            project.fileTree(dir: "${project.projectDir}/build/libs").visit { details ->
                cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'cp', details.file.path, "liferay:/opt/liferay/deploy/"])
            }
        }
    }
}