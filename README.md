# Abraxas

Core utility classes

## Gradle
```kotlin
repositories {
	mavenCentral()
	maven {
		name = "Github Packages"
		url = uri("https://maven.pkg.github.com/elex-project/abraxas")
		credentials {
			username = project.findProperty("github.username") as String
			password = project.findProperty("github.token") as String
		}
	}
}
dependencies {
	implementation("com.elex-project:abraxas:4.0.0")
}
```

## Args
CLI args parser

```java
public static void main(String... args) {
    Args parsedArgs = Args.parse(args);
    Console.writeLine("Message= " + parsedArgs.get("message").orElse("Not exists."));
}
```


---
developed by Elex
https://www.elex-project.com
