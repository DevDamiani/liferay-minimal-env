package io.github.devdamiani.gradle.utils

import org.gradle.api.Project

class SubprojectFilter {
    static void addTasksToSubprojects(Project project, Closure closure) {

        project.subprojects { subproject ->

            def files = fileTree(subproject.projectDir).matching{ exclude { details -> details.directory } }.files
            def containsModuleFiles = files.find { file -> 
                if(file.name.contains("package.json") || file.name.contains("client-extension.yaml") || file.name.contains("build.gradle") ) { 
                    return true
                } 
                return false
            }

            if(containsModuleFiles){

                println "Subproject Name: ${subproject.name}: add tasks dcdeploy"

                closure(subproject)

            }
        }
    }
}