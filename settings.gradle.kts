rootProject.name = "paper-parent"

include("Paper-API", "Paper-Server")

project(":Paper-API").buildFileName = "../gradle/api.gradle.kts"
project(":Paper-Server").buildFileName = "../gradle/server.gradle.kts"
