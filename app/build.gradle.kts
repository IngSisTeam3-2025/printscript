plugins {
    id("shared-build-config")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":lexer"))
    implementation(project(":parser"))
    implementation(project(":interpreter"))
}

application {
    mainClass.set("MainKt")
}


