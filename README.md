# Acagamics Programming Competition Framework
A framework to develop simple 2D Java games.

## Setup
First add the apcf to the game as a git submodule:
`git submodule add git@github.com:manuelliebchen/apcf.git apcf`

For this framwork the game must provide resources like shown in `example-resources/`.
Try:
`cp apcf/example-resources/* src/main/resources`

Then you can use gradle to build it by adding `include("apcf")` to the `settings.gradle.kts` of your game. Given this framework is placed as folder in the Game.

```
Game
|---apcf
|---src
|	`---main
|		|---java
|		`---resources (Where to place the resources)
|---build.gradle.kts
`---settings.gradle.kts
```

## Example

[Here](https://github.com/manuelliebchen/capture) you can find a minimal example for the framework.

Example `build.gradle.kts`:
```
plugins {
    `java-library`
    `java-library-distribution`
	
     id("org.openjfx.javafxplugin") version "0.0.8"

    application
}

repositories {
    mavenCentral()
}

dependencies {	
	implementation(project(":apcf"))

	implementation("org.apache.logging.log4j:log4j-core:2.13.0")
	implementation("org.apache.logging.log4j:log4j-api:2.13.0")
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
//    configuration = "compileOnly"
}

java {
    group = "de.acagamics"
	version = "1.8.1"

    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    
    withJavadocJar()
    withSourcesJar()
}

application{
    mainClassName = "Main"
}
```

Example `settings.gradle.kts`:
```
rootProject.name = "Your Game"

include("apcf")
```

# Acknowledgement

Thank for some parts of the code base to the [Acagamics e.V.](https://acagamics.de/) and particularly:
 - [Jan-Cord Gerken](https://github.com/gnaarf)
 - Claudius Grimm
 - [Gerd Schmidt](https://github.com/Herb1)
 - [Max Klockmann](https://github.com/maxklock)