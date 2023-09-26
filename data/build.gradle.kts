@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies{

    //ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    // coroutine dependency
    implementation(libs.kotlinx.coroutines.core)
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}