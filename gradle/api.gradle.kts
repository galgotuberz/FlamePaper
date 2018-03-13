plugins {
    `maven-publish`
}

val nexusUser: String by extra
val nexusPass: String by extra

val build by tasks
val classes by tasks

base {
    archivesBaseName = "paper-api"
}

configurations {
    "testCompile" { extendsFrom("compileOnly"()) }
}

dependencies {
    compile("com.mojang:authlib:1.5.25")
    compile("commons-lang:commons-lang:2.6")
    compile("com.googlecode.json-simple:json-simple:1.1.1")
    compile("com.google.code.findbugs:jsr305:1.3.9")
    compile("com.google.guava:guava:21.0")
    compile("com.google.code.gson:gson:2.8.0")
    compile("org.yaml:snakeyaml:1.19")
    compile("net.md-5:bungeecord-chat:1.12-SNAPSHOT")
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("org.ow2.asm:asm-all:5.0.4")

    compileOnly("net.sf.trove4j:trove4j:3.0.3")
    compileOnly("co.aikar:fastutil-lite:1.0")
}

val sourcesJar = task<Jar>("sourcesJar") {
    from(java.sourceSets["main"].allSource)
    classifier = "sources"
}

artifacts {
    add("archives", sourcesJar)
}

publishing {
    repositories {
        if ((version as String).endsWith("SNAPSHOT")) {
            maven("https://repo.destroystokyo.com/repository/maven-snapshots/")
        } else {
            maven("https://repo.destroystokyo.com/repository/maven-releases/")
        }.credentials {
            username = nexusUser
            password = nexusPass
        }
    }

    (publications) {
       "mavenJava"(MavenPublication::class) {
           from(components["java"])
           artifact(sourcesJar)
       }
    }
}
