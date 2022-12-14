pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven("https://naver.jfrog.io/artifactory/maven/")
    }
}
rootProject.name = "VaccinationCenterMapApp"
include(":app")
include(":data")
include(":domain")
include(":common")
include(":presentation")
