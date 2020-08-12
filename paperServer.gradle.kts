plugins {
    java
}

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    rootProject.file(".gradle/caches/paperweight/taskCache/mc-libraries.txt").forEachLine { line ->
        if (!line.startsWith("org.lwjgl")) { // lwjgl is definitely client only
            implementation(line)
        }
    }

    implementation(project(":Paper-API"))
    implementation("jline:jline:2.12.1")
    implementation("org.ow2.asm:asm:8.0.1")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.xerial:sqlite-jdbc:3.32.3")
    implementation("mysql:mysql-connector-java:5.1.49")

    testImplementation("junit:junit:4.13")
    testImplementation("org.hamcrest:hamcrest-library:1.3")
}
