import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version = "1.3.31"
val spek_version = "2.0.8"

plugins {
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.31"
    kotlin("plugin.spring") version "1.3.31"
}

group = "com.nmcc.wordconnect"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("http://dl.bintray.com/jetbrains/spek")
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation(group = "org.junit.platform", name = "junit-platform-engine", version = "1.3.0-RC1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<Test> {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
