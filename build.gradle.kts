import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.ultraone"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.sarxos:webcam-capture:0.3.12")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:0.4.0")
}

tasks.test {
    useJUnitPlatform()
}

configurations.implementation {
    exclude("org.bytedeco", "javacpp")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}