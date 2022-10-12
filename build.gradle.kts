import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.0-M5"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    `java-test-fixtures`
}

group = "demo.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.0-M5"))

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // kotlin dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // development dependencies
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // test dependencies
    testImplementation(platform("io.kotest:kotest-bom:5.5.0"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-assertions-json")

    // test fixtures dependencies
    // FIXME: this should be "io.kotest:kotest-property"!
    testFixturesImplementation("io.kotest:kotest-property:5.5.0")

    // test integration dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
}

sourceSets {
    testFixtures {
        java {
            srcDir("src/testFixtures/kotlin")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        val testIntegration by registering(JvmTestSuite::class) {
            testType.set(TestSuiteType.INTEGRATION_TEST)

            dependencies {
                implementation(project)
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

    "testIntegrationImplementation" {
        extendsFrom(configurations.testImplementation.get())
    }

    "testIntegrationRuntimeOnly" {
        extendsFrom(configurations.testRuntimeOnly.get())
    }
}

tasks.check {
    @Suppress("UnstableApiUsage")
    dependsOn(testing.suites.named("testIntegration"))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}
