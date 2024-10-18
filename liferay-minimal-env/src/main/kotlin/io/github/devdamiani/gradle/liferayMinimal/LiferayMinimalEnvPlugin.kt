package io.github.devdamiani.gradle.liferayMinimal

import io.github.devdamiani.gradle.liferayMinimal.extensions.DockerComposeDeployExtension
import io.github.devdamiani.gradle.liferayMinimal.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

abstract class LiferayMinimalEnvPlugin : Plugin<Project> {

    private val taskGroup = "Liferay minimal environment - Docker Compose"

    override fun apply(project: Project) {

        val dcprofilesProperty = if (project.hasProperty("dc.profiles")) project.property("dc.profiles") as String else ""
        val dcprofiles = dcprofilesProperty.split(",")

        project.logger.quiet("[Docker Compose] Profiles: $dcprofiles")

        val dcinitTask = project.tasks.create("dcinit", DockerComposeInitTask::class.java) {
            it.dependsOn("clean", "createDockerfile")

            it.group = taskGroup
            it.description = "Build project and start Docker Compose."
            it.profiles = dcprofiles
        }

        project.tasks.create("dcup", DockerComposeUpTask::class.java) {
            it.dependsOn( "dockerDeploy")

            it.group = taskGroup
            it.description = "Run Docker Compose and Build Docker Images."
            it.profiles = dcprofiles
        }

        project.tasks.create("dcstart", DockerComposeStartTask::class.java) {
            it.group = taskGroup
            it.description = "Start Docker Compose Services."
            it.profiles = dcprofiles
        }

        project.tasks.create("dcstop", DockerComposeStopTask::class.java) {
            it.group = taskGroup
            it.description = "Stop Docker Compose Services."
            it.profiles = dcprofiles
        }

        project.tasks.create("dcdown", DockerComposeDownTask::class.java) {
            it.group = taskGroup
            it.description = "Stop and Remove Docker Compose Containers and Volumes."
            it.profiles = dcprofiles
        }

        project.tasks.create("createdump", GenerateDumpTask::class.java) {
            it.group = taskGroup
            it.description = "Remove old dump files and create a new one."
        }

        project.subprojects { subproject ->

            subproject.tasks.matching { it.name == "deploy" }.all { deployTask: Task ->

                subproject.extensions.create("dcdeploy", DockerComposeDeployExtension::class.java)
                subproject.logger.info("subproject with task deploy: $subproject")

                subproject.tasks.create("dcdeploy", DockerComposeDeploy::class.java) {
                    it.dependsOn("deploy")
                    it.group = taskGroup
                    it.description = "Deploy build files to the container."
                }

                dcinitTask.dependsOn("clean", deployTask)
            }
        }
    }

}
