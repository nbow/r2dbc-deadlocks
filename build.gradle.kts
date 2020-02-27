
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    id("org.jlleitschuh.gradle.ktlint") version "9.0.0"
}

group = "com.r2dbc.deadlock"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    maven { url = uri("https://repo.spring.io/libs-snapshot") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.0.0.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.r2dbc:r2dbc-mssql:0.8.2.BUILD-SNAPSHOT")
    implementation("io.r2dbc:r2dbc-pool:0.8.2.BUILD-SNAPSHOT")
    implementation("io.r2dbc:r2dbc-spi:0.8.2.BUILD-SNAPSHOT")
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc:7.4.1.jre11")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:0.1.0.M3")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
