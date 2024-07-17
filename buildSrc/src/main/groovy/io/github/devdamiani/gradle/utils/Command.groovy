package io.github.devdamiani.gradle.utils

class Command {
    
    public void execute(def project, List<String> commandLineContent, List<String> profiles = [], String setWorkingDir = null) {

        def execResult = project.exec {
            
            workingDir = project.rootDir

            if (setWorkingDir != null) {
                workingDir setWorkingDir
            }

            if (profiles.size() > 0) {
                environment 'COMPOSE_PROFILES', "${profiles.join(' ')}"
            }

            commandLine commandLineContent
        }

        if (execResult.exitValue != 0) {
            throw new RuntimeException("Command failed: ${commandLine.join(' ')}")
        }
    }
}


