package io.github.devdamiani.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.api.tasks.Input

import io.github.devdamiani.gradle.utils.Command

class DockerComposeStartTask extends DefaultTask {

    private List<String> profiles = []

    @Option(option = "profiles", description = "Docker compose profiles default value is spring")
    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    @Input
    public List<String> getProfiles() {
        return this.profiles;
    }

    @TaskAction
    void init() {

        String dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        def cmd = new Command()

        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'start'], getProfiles())
    }
}