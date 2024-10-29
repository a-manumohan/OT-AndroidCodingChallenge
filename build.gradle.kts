import com.diffplug.gradle.spotless.SpotlessExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.spotless) apply false
}
subprojects {
    apply {
        plugin(rootProject.libs.plugins.spotless.get().pluginId)
    }
    configure<SpotlessExtension> {
        val ktlintVersion = "1.4.0"
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            ktlint(ktlintVersion).setEditorConfigPath("${project.rootDir}/.editorconfig")
//            ktlint(ktlintVersion)
            toggleOffOn() // Allow toggling Spotless off and on within code files using comments
            trimTrailingWhitespace()
            endWithNewline()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint(ktlintVersion) // Apply ktlint to Gradle Kotlin scripts
        }
    }
}
