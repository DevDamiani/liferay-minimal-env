package io.github.devdamiani.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory

import io.github.devdamiani.gradle.utils.Command

class DockerComposeInitTask extends DefaultTask {

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

        if(!hasDockerComposeFile()){
            println "Creating Docker compose content..."
            applyDockerResources()
        }

        String dockerComposeFile = "${project.rootDir}/docker-compose.yaml"
        def cmd = new Command()

        cmd.execute(project, ['./gradlew', 'clean', 'deploy', 'createdockerfile'], getProfiles())
        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'down', '-v'], getProfiles())
        cmd.execute(project, ['docker', 'compose', "-f", dockerComposeFile, 'up', '-d', '--build', '-V', '--remove-orphans'], getProfiles())
    }

    void applyDockerResources(){

        def resourceFileName = 'content.zip'

        def outputDir = project.file("${project.projectDir}/")

        def zipOutputFile = project.file("${project.projectDir}/.gradle")
        def zipFile = project.file("${project.projectDir}/.gradle/${resourceFileName}")

        extractResourceFile(resourceFileName, zipOutputFile)

        extractZipFile(zipFile, outputDir)

        deleteFile(zipFile)

    }

    void extractResourceFile(String resourceFileName, File outputLocation){
        def inputStream = this.getClass().getResourceAsStream('/' + resourceFileName)

        if (inputStream) {
            File destinationFile = new File(outputLocation, resourceFileName)

            outputLocation.mkdirs()
            destinationFile.withOutputStream { outputStream ->
                outputStream << inputStream
            }
            
            inputStream.close()
        } 
    }

    void extractZipFile(File fileLocation, File outputLocation){
        if (!fileLocation.exists()) {
            return
        }

        project.copy {
            from project.zipTree(fileLocation)
            into outputLocation
        }

    }


    void deleteFile(File file){

        if (file.exists()) {
            if (!file.delete()) {
                println "Falha ao deletar o arquivo: $file"
            }
        } 
    }

    boolean hasDockerComposeFile(){

        def files = project.fileTree(project.projectDir).matching{ exclude { details -> details.directory } }.files
        def containsModuleFiles = files.find { file -> 
            if(file.name.contains("compose.yaml") || file.name.contains("compose.yml") || file.name.contains("docker-compose.yml") || file.name.contains("docker-compose.yaml") ) { 
                return true
            } 
            return false
        }


        return containsModuleFiles
    }

}