val serializationVersion = "1.1.0"
val ktorVersion = "1.5.2"


plugins {
    kotlin("multiplatform") version "1.4.31"
    application
    kotlin("plugin.serialization") version "1.4.31"
}

group = "org.learning"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        withJava()
    }
    js {
        browser {
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }
        val jsMain by getting {
            application {
                mainClass.set("org.learning.adaptor.Server")
            }
            dependencies {
            }
        }
    }
}