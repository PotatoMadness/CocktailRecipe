pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CocktailRecipe"
include(":app")
include(":feature:home")
include(":core:data")
include(":core:common")
include(":feature:favorites")
include(":feature:myrecipe")
include(":core:ui")
include(":feature:detail")
include(":core:designsystem")
include(":core:database")
include(":core:domain")
