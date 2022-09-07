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
//tasks.create("SSD")


task<JavaExec>("runtMoveNetLighting")  {
   main = "live_cv.pose_detection.MoveNetLightingKt"
   classpath = java.sourceSets["main"].runtimeClasspath
}
task<JavaExec>("runSSD"){
    main = "live_cv.object_detection.SSDMobileNetV1Kt"
    classpath = java.sourceSets["main"].runtimeClasspath
}
task<JavaExec>("runSSDMobileNetV1"){
    main = "live_cv.object_detection.SSDKt"
    classpath = java.sourceSets["main"].runtimeClasspath

}
task<JavaExec>("runEfficientNetB4"){
    main = "live_cv.object_detection.EfficientNetB4Kt"
    classpath = java.sourceSets["main"].runtimeClasspath

}