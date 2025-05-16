plugins {
    kotlin("jvm") version "2.0.21"
    id("com.vaadin") version "24.6.4"
    application
}

group = "com.dankim"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // vaadin
    implementation("com.vaadin:vaadin-core:24.7.2")
    implementation("com.github.mvysny.vaadin-boot:vaadin-boot:13.3")
    implementation("com.github.mvysny.karibudsl:karibu-dsl:2.3.2")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")

    // database
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation("org.postgresql:postgresql:42.7.5")

    // misc
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.dankim.MainKt")
}