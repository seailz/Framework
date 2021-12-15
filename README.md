# Plugin-Framework
A simple plugin framework that is mainly used for completing commissions


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
    <groupId>com.github.NegativeKB</groupId>
    <artifactId>Plugin-Framework</artifactId>
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
      <pattern>dev.negativekb.api</pattern>
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
      <pattern>dev.negativekb.api</pattern>
      <shadedPattern>dev.negaivekb.myplugin.api</shadedPattern>
    </relocation>
  </relocations>
</configuration>
```
