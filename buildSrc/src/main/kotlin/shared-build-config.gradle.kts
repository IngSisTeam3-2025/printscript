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
            naming:
              MatchingDeclarationName:
                active: false
            style:
              SpacingBetweenPackageAndImports:
                active: true
              MaxLineLength:
                active: false
              LoopWithTooManyJumpStatements:
                active: false
              ReturnCount:
                active: false
              UseCheckOrError:
                active: false
              WildcardImport:
                active: true
              MagicNumber:
                active: false
            complexity:
              NestedBlockDepth:
                active: false
              LongParameterList:
                active: false
              TooManyFunctions:
                active: false
              CyclomaticComplexMethod:
                active: false
              LongMethod:
                active: false
            """
        )
    )
}

kotlin { jvmToolchain(17) }

tasks.check { dependsOn("detekt","spotlessCheck", "koverVerify") }

tasks.named("build") {
    dependsOn("spotlessCheck", "check")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "17"
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt")
        ktlint().editorConfigOverride(
            mapOf(
                "indent_size" to "4",
                "insert_final_newline" to "true"
            )
        )
        trimTrailingWhitespace()
    }
}