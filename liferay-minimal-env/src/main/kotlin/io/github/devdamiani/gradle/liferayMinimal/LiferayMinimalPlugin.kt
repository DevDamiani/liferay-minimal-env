package io.github.devdamiani.liferayMinimal

import io.github.devdamiani.liferayMinimal.tasks.*
import io.github.devdamiani.liferayMinimal.utils.SubprojectFilter
import org.gradle.api.Plugin
import org.gradle.api.Project

class LiferayMinimalEnvPlugin : Plugin<Project> {

    private val taskGroup = "Liferay minimal environment - Docker Compose"

    override fun apply(project: Project) {

        val dcprofilesProperty = if (project.hasProperty("dc.profiles")) project.property("dc.profiles") as String else ""
        val dcprofiles = dcprofilesProperty.split(",")

        println("Current profiles: $dcprofiles")

        project.tasks.register("dcinit", DockerComposeInitTask::class.java) {
            it.group = taskGroup
            it.description = "Build project and start and build Docker Compose."
            it.profiles = dcprofiles
        }

        project.tasks.register("dcup", DockerComposeUpTask::class.java) {
            it.group = taskGroup
            it.description = "Run Docker Compose and Build Docker Images."
            it.profiles = dcprofiles
        }

        project.tasks.register("dcstart", DockerComposeStartTask::class.java) {
            it.group = taskGroup
            it.description = "Start Docker Compose Services."
            it.profiles = dcprofiles
        }

        project.tasks.register("dcstop", DockerComposeStopTask::class.java) {
            it.group = taskGroup
            it.description = "Stop Docker Compose Services."
            it.profiles = dcprofiles
        }

        project.tasks.register("dcdown", DockerComposeDownTask::class.java) {
            it.group = taskGroup
            it.description = "Stop and Remove Docker Compose Containers."
            it.profiles = dcprofiles
        }

        project.tasks.register("createdump", GenerateDumpMySQL::class.java) {
            it.group = taskGroup
            it.description = "Remove old dump files and create a new one."
        }

        SubprojectFilter.addTasksToSubprojects(project) { proj ->
            proj.tasks.register("dcdeploy", DockerComposeDeploy::class.java) {
                it.group = taskGroup
                it.description = "Deploy build files to the container."
            }
        }
    }
}
