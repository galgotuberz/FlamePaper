import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer
import de.sebastianboegl.gradle.plugins.shadow.transformers.Log4j2PluginsFileTransformer
import java.time.Instant

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("de.sebastianboegl.gradle.plugins:shadow-log4j-transformer:2.1.1")
    }
}

plugins {
    id("com.github.johnrengelman.shadow") version "2.0.2"
    id("ru.vyarus.animalsniffer") version "1.4.3"
}

val minecraftVersion: String by extra

val jar: Jar by tasks
val shadowJar: ShadowJar by tasks
val test: Test by tasks
val build by tasks

base {
    archivesBaseName = "paper-server"
}

repositories {
    // minecraft-server is installed to maven local
    mavenLocal()
}

dependencies {
    compile(project(":Paper-API"))

    compile("io.netty:netty-all:4.1.21.Final")
    compile("org.spigotmc:minecraft-server:$minecraftVersion-SNAPSHOT")
    compile("net.sf.jopt-simple:jopt-simple:5.0.4")
    compile("net.sf.trove4j:trove4j:3.0.3")
    compile("net.minecrell:terminalconsoleappender:1.0.0")
    compile("org.apache.logging.log4j:log4j-core:2.8.1")
    compile("org.apache.logging.log4j:log4j-iostreams:2.8.1")

    runtime("mysql:mysql-connector-java:5.1.45")
    runtime("org.xerial:sqlite-jdbc:3.21.0.1")
    runtime("net.java.dev.jna:jna:4.4.0")
    runtime("org.apache.logging.log4j:log4j-slf4j-impl:2.8.1")

    signature("org.codehaus.mojo.signature:java18:1.0@signature")
}

jar {
    manifest {
        attributes(mapOf(
            "Main-Class" to "org.bukkit.craftbukkit.Main",
            "Implementation-Title" to "CraftBukkit",
            "Implementation-Version" to "git-Paper-" + describe(),
            "Implementation-Vendor" to Instant.now().toString(),
            "Specification-Title" to "Bukkit",
            "Specification-Version" to version,
            "Specification-Vendor" to "Bukkit Team"
        ))

        attributes(mapOf("Sealed" to "true"), "net/bukkit/")
        attributes(mapOf("Sealed" to "true"), "com/bukkit/")
        attributes(mapOf("Sealed" to "true"), "org/bukkit/")
    }
}

shadowJar {
    classifier = null
    relocate("jline", "org.bukkit.craftbukkit.libs.jline")
    relocate("org.bukkit.craftbukkit", "org.bukkit.craftbukkit.v$minecraftVersion") {
        exclude("org.bukkit.craftbukkit.Main*")
    }
    relocate("net.minecraft.server", "net.minecraft.server.v$minecraftVersion")
    transform(Log4j2PluginsFileTransformer::class.java)
    mergeServiceFiles()
}

test {
    exclude("org/bukkit/craftbukkit/inventory/ItemStack*Test.class")
}

build.dependsOn(shadowJar)

fun describe(): String {
    return cmd("git", "describe") ?: cmd("git", "log", "--pretty=format:\"%h\"") ?: "unknown"
}

fun cmd(vararg args: String): String? {
    val p = ProcessBuilder().command(*args).directory(rootProject.projectDir).start()
    p.inputStream.bufferedReader().use {
        return it.readLine()
    }
}

inline operator fun <T : Task> T.invoke(a: T.() -> Unit): T = apply(a)
