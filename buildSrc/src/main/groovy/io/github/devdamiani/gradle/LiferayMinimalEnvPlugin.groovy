package io.github.devdamiani.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import io.github.devdamiani.gradle.utils.SubprojectFilter
import io.github.devdamiani.gradle.tasks.DockerComposeInitTask
import io.github.devdamiani.gradle.tasks.DockerComposeUpTask
import io.github.devdamiani.gradle.tasks.DockerComposeStartTask
import io.github.devdamiani.gradle.tasks.DockerComposeStopTask
import io.github.devdamiani.gradle.tasks.DockerComposeDownTask
import io.github.devdamiani.gradle.tasks.GenerateDumpMySQL
import io.github.devdamiani.gradle.tasks.DockerComposeDeploy


class LiferayMinimalEnvPlugin implements Plugin<Project> {

    private def taskGroup = 'Liferay minimal environment - Docker Compose'

    @Override
    void apply(Project project) {


        def dcprofilesProperty = project.hasProperty('dc.profiles') ? project.property('dc.profiles') : ''
        def dcprofiles = dcprofilesProperty.tokenize()

        println "Current profiles: ${dcprofiles}"

        project.tasks.register('dcinit', DockerComposeInitTask) { 
            group = this.taskGroup
            description = 'Build project and start and build Docker Compose.'
            profiles = dcprofiles  
        }

        project.tasks.register('dcup', DockerComposeUpTask) { 
            group = taskGroup
            description = 'Run Docker Compose and Build Docker Images.'
            profiles = dcprofiles  
        }

        project.tasks.register('dcstart', DockerComposeStartTask) {
            group = taskGroup
            description = 'Start Docker Compose Services.'
        	profiles = dcprofiles  
        }

        project.tasks.register('dcstop', DockerComposeStopTask) { 
            group = taskGroup
            description = 'Stop Docker Compose Services.'
            profiles = dcprofiles  
        }

        project.tasks.register('dcdown', DockerComposeDownTask) {
            group = taskGroup
            description = 'Stop and Remove Docker Compose Containers.'
            profiles = dcprofiles  
        }

        project.tasks.register('createdump', GenerateDumpMySQL) {
            group = taskGroup
            description = 'Remove old dump files and create a new one.'
        }

        SubprojectFilter.addTasksToSubprojects(project) { proj ->

            proj.tasks.register('dcdeploy', DockerComposeDeploy) {
                group = taskGroup
                description = 'Deploy build files to the container.'
            }
        }
    }
}