import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.OutputStream

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://jitpack.io/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    implementation("com.github.monun:tap:3.6.0")
    implementation("com.github.monun:kommand:1.0.0")
    implementation("com.github.donghune:minecraft-namulibrary:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:1.4.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:1.4.0")
    implementation("io.insert-koin:koin-core-ext:3.0.2")
}

/*//ProtocolLib
repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}
dependencies {
    compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
}*/

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourcesJar") {
        from(sourceSets["main"].allSource)
        archiveClassifier.set("sources")
    }

    fun ShadowJar.pluginJar(classifier: String = "") {
        archiveBaseName.set(project.property("pluginName").toString())
        archiveVersion.set(project.property("version").toString()) // For bukkit plugin update
        archiveClassifier.set(classifier)
        from(sourceSets["main"].output)
        configurations = listOf(project.configurations.implementation.get().apply { isCanBeResolved = true })
    }

    create<ShadowJar>("pluginJar") {
        pluginJar()
        relocate("com.github.monun.kommand", "${rootProject.group}.${rootProject.name}.kommand")
        relocate("com.github.monun.tap", "${rootProject.group}.${rootProject.name}.tap")
        relocate("com.github.donghune.namulibrary", "${rootProject.group}.${rootProject.name}.namulibrary")
        relocate("com.github.shynixn.mccoroutine", "${rootProject.group}.${rootProject.name}.mccoroutine")
    }

    create<ShadowJar>("makePluginJar") {
        pluginJar("SNAPSHOT")
    }

    build {
        dependsOn(named("pluginJar"))
    }

    create<Copy>("copyToServer") {
        val plugins = File("/Users/namu/MinecraftProjects/1.16.5/plugins")
        named("makePluginJar").run {
            from(this)
            into(plugins)
        }
    }

    create<DefaultTask>("setupWorkspace") {
        doLast {
            val versions = arrayOf(
                "1.16.5"
            )
            val buildtoolsDir = file(".buildtools")
            val buildtools = File(buildtoolsDir, "BuildTools.jar")

            val maven = File(System.getProperty("user.home"), ".m2/repository/org/spigotmc/spigot/")
            val repos = maven.listFiles { file: File -> file.isDirectory } ?: emptyArray()
            val missingVersions = versions.filter { version ->
                repos.find { it.name.startsWith(version) }?.also { println("Skip downloading spigot-$version") } == null
            }.also { if (it.isEmpty()) return@doLast }

            val download by registering(de.undercouch.gradle.tasks.download.Download::class) {
                src("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar")
                dest(buildtools)
            }
            download.get().download()

            runCatching {
                for (v in missingVersions) {
                    println("Downloading spigot-$v...")

                    javaexec {
                        workingDir(buildtoolsDir)
                        main = "-jar"
                        args = listOf("./${buildtools.name}", "--rev", v)
                        // Silent
                        standardOutput = OutputStream.nullOutputStream()
                        errorOutput = OutputStream.nullOutputStream()
                    }
                }
            }.onFailure {
                it.printStackTrace()
            }
            buildtoolsDir.deleteRecursively()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(project.property("pluginName").toString()) {
            artifactId = project.name
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}