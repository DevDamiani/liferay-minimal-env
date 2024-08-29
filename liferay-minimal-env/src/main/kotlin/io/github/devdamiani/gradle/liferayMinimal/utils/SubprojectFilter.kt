package io.github.devdamiani.liferayMinimal.utils

import org.gradle.api.Project
import java.io.File

object SubprojectFilter {

    fun addTasksToSubprojects(project: Project, closure: (Project) -> Unit) {
        project.subprojects { subproject ->

            val files: Set<File> = subproject.fileTree(subproject.projectDir)
                .matching { it.exclude { details -> details.isDirectory } }
                .files

            val containsModuleFiles = files.any { file ->
                file.name.contains("package.json") ||
                        file.name.contains("client-extension.yaml") ||
                        file.name.contains("build.gradle")
            }

            if (containsModuleFiles) {
                println("Subproject Name: ${subproject.name}: add tasks dcdeploy")

                closure(subproject)
            }
        }
    }
}
