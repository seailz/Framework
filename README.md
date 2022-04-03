# Negative Games - Framework
An expanded plugin library which allows more effective and rapid plugin development.

## ✨Maven Repo

### Repository

```xml
<repository>     
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

### Dependency

```xml
<dependency>
    <groupId>com.github.Negative-Games</groupId>
    <artifactId>Framework</artifactId>
    <version>{VERSION}</version>
    <scope>compile</scope>
</dependency>
```

### Build Configuration
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
An example would be
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

## Gradle Repo
Coming soon.
