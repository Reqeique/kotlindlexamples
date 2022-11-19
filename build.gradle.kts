import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.ultraone"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven( "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.sarxos:webcam-capture:0.3.12")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.4.0")
    implementation("ai.djl.huggingface:tokenizers:0.18.0")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:0.4.0")
    implementation ("dev.reimer:progressbar-ktx:0.1.0")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")



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
task<JavaExec>("runResNet18v2"){
    main = "live_cv.object_detection.ResNet18v2Kt"
    classpath = java.sourceSets["main"].runtimeClasspath
}
task<JavaExec>("runDistilBertUCSST2EN"){
    //main = "live_cv.object_detection.ResNet18v2Kt"
    standardInput = System.`in`
    main = "nlp.sentiment_analysis.DistilBertUCSST2ENKt"
    classpath = java.sourceSets["main"].runtimeClasspath
}