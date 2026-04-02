plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "com.xrosstools"
version = "1.7.1"

val sandbox  : String by project

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2025.3.3")
        bundledPlugin("com.intellij.java")
    }

    compileOnly(files("$sandbox/com.xrosstools.idea.gef.zip"))
    implementation(files("libs/dom4j-1.6.1.jar"))
}

intellijPlatform {
    instrumentCode = true
    buildSearchableOptions = false

    pluginConfiguration {
        name = "Xross State Machine Editor"

        ideaVersion {
            sinceBuild = "193.6911.18"
        }

        changeNotes = """
            <em>1.7.1</em> Fix bug when generating factory class.<br>
            <em>1.7.0</em> Support generate state entry/exit action and transition action/guard. Optimize icons.<br>
            <em>1.6.7</em> Fix code generation bug, simplify reference function.<br>
            <em>1.6.6</em> Optimize code generation.<br>
            <em>1.6.5</em> Minor improvement about node title color and node layout.<br>
            <em>1.6.4</em> Fix CommonStyleRouter bug when node tries to connect itself.<br>
            <em>1.6.3</em> Clean up used icons.<br>
            <em>1.6.2</em> Auto fill diagram name and Optimize code generation.<br>
            <em>1.6.1</em> Fix bug when there is no method found for a given name.<br>
            <em>1.6.0</em> Support find usage and rename from source code to model file.<br>
            <em>1.5.0</em> Support jump from diagram to source code method.<br>
            <em>1.4.0</em> Support delegate actions/guard to method other than default ones.<br>
            <em>1.3.2</em> Transfer state machine name into class name with camel case style.<br>
            <em>1.3.1</em> Use relative path to load factory in generated helper class.<br>
            <em>1.3.0</em> Support helper class and test case generation.<br>
            <em>1.2.0</em> Add label  property for state node and event.<br>
            <em>1.1.1</em> Fix typo and update to new GEF dependency.<br>
            <em>1.1.0</em> Fix create model bug. New features: <br>
            1) child state machine reference<br>
            2) transition guard<br>
            3) delete state machine, event, node and transition from outline window.<br>
            <em>1.0.5</em> Follow IDEA market place release standard.<br>
            <em>1.0.4</em> Optimize node link to itself for multiple links case.<br>
            <em>1.0.3</em> Allow node link to itself.<br>
            <em>1.0.2</em> Update to latest GEF, fix NPE when selecting non visual element<br>
            <em>1.0.1</em> Update to latest GEF<br>
            <em>1.0.0</em> IDEA version that supports editing state machines<br>
        """.trimIndent()
    }

    pluginVerification {
        ides {
            // 验证最老和最新的目标版本
            ide("IC-2018.3.6")
            ide("IC-2020.3.4")
            ide("IC-2025.3.3")
        }
    }
}

intellijPlatformTesting {
    runIde {
        register("runWithLocalPlugins") {
            plugins {
                val pluginFiles = file(sandbox).listFiles()
                pluginFiles.forEach { file ->
                    if (!file.name.contains(project.name)) {
                        localPlugin(file.absolutePath)
                    }
                }
            }
        }
    }
}

tasks.named("runIde") {
    dependsOn("runWithLocalPlugins")
}

tasks {
    buildPlugin {
        archiveFileName.set("${project.name}.zip")
    }
}
