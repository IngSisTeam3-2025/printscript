plugins {
    id("io.gitlab.arturbosch.detekt")
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlinx.kover")
    kotlin("jvm")
}

repositories { mavenCentral() }

dependencies { testImplementation(kotlin("test")) }

tasks.test { useJUnitPlatform() }

detekt {
    buildUponDefaultConfig = true
    config.setFrom(
        resources.text.fromString(
            """
            style:
              SpacingBetweenPackageAndImports:
                active: true
              MaxLineLength:
                active: true
                maxLineLength: 120
              LoopWithTooManyJumpStatements:
                active: false
              ReturnCount:
                active: false
              UseCheckOrError:
                active: false
            complexity:
              TooManyFunctions:
                active: false
              CyclomaticComplexMethod:
                active: false
              LongMethod:
                active: false
            exceptions:
              TooGenericExceptionThrown:
                active: false
            """.trimIndent()
        )
    )
}

kotlin { jvmToolchain(21) }

spotless {
    kotlin {
        ktlint().editorConfigOverride(
            mapOf("indent_size" to "4","insert_final_newline" to "true")
        )
    }
}

tasks.check { dependsOn("detekt","spotlessCheck", "koverVerify") }

tasks.named("build") {
    dependsOn("spotlessApply", "check")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "20"
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt")
        trimTrailingWhitespace()
    }
}

kover {
}