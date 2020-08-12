pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://files.minecraftforge.net/maven/")
        gradlePluginPortal()
    }
}

rootProject.name = "Paper"
include("Paper-API", "Paper-Server")

project(":Paper-API").buildFileName = "../paperApi.gradle.kts"
project(":Paper-Server").buildFileName = "../paperServer.gradle.kts"
