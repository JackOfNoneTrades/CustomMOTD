
pluginManagement {
    repositories {
        maven {
            // RetroFuturaGradle
            name = "GTNH Maven"
            url = uri("https://nexus.gtnewhorizons.com/repository/public/")
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
<<<<<<< HEAD
    id("com.gtnewhorizons.gtnhsettingsconvention") version("1.0.49")
=======
    id("com.gtnewhorizons.gtnhsettingsconvention") version("1.0.51")
>>>>>>> 8419e3e (Updated buildscript)
}
