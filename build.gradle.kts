plugins {	
    `java-library`
    `java-library-distribution`
	
    id("org.openjfx.javafxplugin")
}

repositories {
    mavenCentral()
}

dependencies {
	implementation("org.apache.logging.log4j:log4j-core:2.13.0")
    implementation("org.apache.logging.log4j:log4j-api:2.13.0")
	
	implementation("org.yaml:snakeyaml:1.25")

    implementation("com.jfoenix:jfoenix:9.0.1")

    implementation("io.github.classgraph:classgraph:4.8.67")

    implementation("com.beust:jcommander:1.78")

	testImplementation("junit:junit:4.13-rc-2")
}


tasks.test {
    useJUnit()

    maxHeapSize = "1G"
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
    configuration = "compileOnly"
}

java {
    group = "de.acagamics"
	version = "1.0.0"

    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    
    withJavadocJar()
    withSourcesJar()

}

tasks.register<Javadoc>("allJavaDoc") {

}

tasks.javadoc {
    options.memberLevel = JavadocMemberLevel.PUBLIC
}
