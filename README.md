<img src="https://cdn.joehosten.me/negative-games/assets/Framework.png">
<hr>

# Framework
[![](https://jitpack.io/v/Negative-Games/Framework.svg)](https://jitpack.io/#Negative-Games/Framework) ![licence](https://img.shields.io/github/license/negative-games/framework) ![GitHub commits since latest release (by date)](https://img.shields.io/github/commits-since/negative-games/framework/latest) ![GitHub Workflow Status](https://img.shields.io/github/workflow/status/negative-games/framework/CodeQL) 

An expanded plugin library which allows more effective and rapid plugin development.   
Latest version: `2.3.1`


## Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.


## Documentation

https://github.com/Negative-Games/Framework/wiki  
https://framework.docs.negative.games/


## License

[We use the MIT licence for this project. Please read it here.](https://choosealicense.com/licenses/mit/)


## Metrics Usage
If you would like to help us gather statistical & usage data for Framework via [Metrics](https://bstats.org), please add the following in your `onEnable()` method:
```java
enableFrameworkUsageTracking()
```
## Maven & Gradle Repositories

### ✨Maven Repository✨

#### Repository

```xml
<repository>     
    <id>negative-games</id>
    <url>https://repo.negative.games/repository/negative-games/</url>
</repository>
```

#### Dependency

```xml
<dependency>
      <groupId>games.negative.framework</groupId>
      <artifactId>Framework</artifactId>
      <version>{VERSION}</version>
      <scope>compile</scope>
</dependency>
```

#### Build Configuration

Add this to your build configuration for this to work correctly.

```xml
<configuration>
  <relocations>
    <relocation>
      <pattern>games.negative.framework</pattern>
      <shadedPattern>{YOUR PACKAGE NAME}</shadedPattern>
    </relocation>
  </relocations>
</configuration>
```

#### Example

An example would be:

```xml
<configuration>
  <relocations>
    <relocation>
      <pattern>games.negative.framework</pattern>
      <shadedPattern>me.name.myplugin.framework</shadedPattern>
    </relocation>
  </relocations>
</configuration>
```

To view an example in a full pom, head
to [this link](https://gist.github.com/joeecodes/f0d2da7807b256e44cce7da3be0bb188).

### ✨Gradle Repo✨
#### Repository

```groovy
repositories {
    mavenCentral()
    maven { url 'https://repo.negative.games/repository/negative-games/' }
}
```
#### Dependencies

```groovy
dependencies {
    implementation 'games.negative.framework:Framework:{VERSION}'
}
```

To mask the dependency, add this to your plugins section

```groovy
plugins {
    id 'java'
    id "com.github.johnrengelman.shadow" version "7.1.2"
}
```

And then make a shadowJar section and put the following

```groovy
shadowJar {
    archiveBaseName.set("${id}-${version}")
    archiveClassifier.set("")
    archiveVersion.set("")
    
    relocate "games.negative.framework", "${group}.libs.plugin"
}
```

If you are wondering what `${group}` and `${id}` are, it is this:

```groovy
def id = "MyPlugin" // Replace with the plugin name
def group = 'games.negative' // Replace with your group id
def version = '1.0-SNAPSHOT' // Replace with the version
```
## Support

For support, join https://discord.negative.games, create an issue card or email us at negativegames.dev@gmail.com.


## Feedback

If you have any feedback, please reach out to us at https://discord.negative.games


## Authors
<a href="https://github.com/negative-games/framework/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=negative-games/framework" />
</a>
