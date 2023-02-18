plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

group = "com.nekozouneko"
version = if (hasProperty("version")) findProperty("version") as String else ""

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<ProcessResources> {
        filesMatching("*.yml") {expand(rootProject.properties)}
    }
}