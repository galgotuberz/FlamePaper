plugins {
    java
}

val javaVersion = JavaVersion.VERSION_1_8

group = "com.destroystokyo.paper"

subprojects {
    apply {
        plugin("java")
    }

    group = rootProject.group

    repositories {
        mavenCentral()
        maven("https://repo.md-5.net/content/repositories/releases/")
        maven("https://repo.aikar.co/content/groups/aikar/")
        maven("https://repo.destroystokyo.com/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://oss.sonatype.org/content/groups/public/")
    }

    dependencies {
        testCompile("junit:junit:4.12")
        testCompile("org.hamcrest:hamcrest-library:1.3")
    }

    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-proc:none")
    }
}
