/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/8.1/samples
 */

repositories {
    mavenCentral()
}

plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

application {
    mainClass.set("general.Main")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}
