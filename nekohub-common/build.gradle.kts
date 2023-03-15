import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.nekozouneko"
version = rootProject.version

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("com.google.guava:guava:31.1-jre")
    compileOnly("net.md-5:bungeecord-chat:1.19-R0.1-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("NekoHub-Common")
    archiveClassifier.set("")
    archiveVersion.set("")
}

