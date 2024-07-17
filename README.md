# How Started the Project

## Prerequisites

Before starting, ensure you have the following prerequisites:


- **Java 11:** Install Java 11 from SDKMAN using the Zulu distribution. If you haven't already installed SDKMAN, you can do so by following the instructions at [SDKMAN Installation Guide](https://sdkman.io/install). Once SDKMAN is installed, use the following command to install Java 11 Zulu:

  ```bash
   sdk install java 11.0.19-zulu 
  ```

- **Docker:** Ensure that Docker is installed on your system. You can download and install Docker from the official website: [Docker Installation Guide](https://docs.docker.com/get-docker/).

- **Docker Access for Non-Root User:** Configure Docker to allow a non-root user to access Docker. You can set this up by following Docker's official guide:  [Manage Docker as a Non-Root User](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user).


- **Blade CLI:** Ensure that Docker is installed on your system. You can download and install Docker from the official website [Installing Blade CLI](https://help.liferay.com/hc/en-us/articles/360017885232-Installing-Blade-CLI-).

## Setting Up the Environment

### Initialize the Development Container Environment.

Commands must be executed at the root of the project. To initialize the environment, use the following command:

- This will compile the project, clean containers and volumes, create Docker images, and start the environment:

  ```bash
  blade gw dcinit
  ```

- If you need to rebuild Docker images and create containers without performing a full initialization, use the following command:

  ```bash
  blade gw dcup
  ```

- You can start or stop containers individually with the following commands:

  - Start containers:

    ```bash
    blade gw dcstart
    ```

  - Stop containers:

    ```bash
    blade gw dcstop
    ```

- To shut down the entire environment (stop containers and clean up), use the following command:

    ```bash
    blade gw dcdown
    ```

### Set Up Docker Compose Profiles. **(Optional)**
Using Docker Compose profiles allows you to manage different configurations for your applications within a single composition file. To use this concept when initializing the environment, you have a couple of ways:

1. Place the configurations in the `local.properties` file under the `dc.profiles` property. For instance, create the property: `dc.profiles=default` in local.properties.

  ```properties
  ## file: local.properties
  dc.profiles=default
  ```

2. When running a Gradle task, pass the profile as a parameter. For example, use `blade gw dcinit --profiles=default`.

To view all Docker Compose profiles, you can check the file in the project's root directory named docker-compose.yaml. Each service has its profile, and if the property is not declared in the file, it belongs to the 'default' profile.

### Creating a New Local Application State. **(Optional)**

To create and share a new state, simply use the `createdump` command. This will replace the previous state with the current state of Liferay on your machine. After creating the new state, follow the project gitflow to the development branch and share it.

  ```bash
  blade gw createdump
  ```

## To deploy a module or Client Extension in liferay:

- Access the module or Client Extension folder in the terminal and run the command:

  ```bash
  blade gw dcdeploy
  ```

## Accessing Local Environment

To access the environment hosted on `localhost:8080`, you can use the following URL in your web browser:

http://localhost:8080


You can log in using the following default credentials:

**Email Adress:** test@liferay.com

**Password:** test