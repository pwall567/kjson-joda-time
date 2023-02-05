# kjson-joda-time

Joda Time Adapters for [`kjson`](https://github.com/pwall567/kjson)

## Background

Prior to the release of Java 1.8, the Joda Time library provided data and time implementations superior to those
available in the standard JDK.
It has now become redundant, with the release of the `java.time.xxx` classes in JDK 1.8, but many applications still use
the Joda Time libraries, and it is likely to be some time before all those older applications are converted or replaced.

The `kjson-joda-time` library provides custom serialization and deserialization classes to allow the use of the
[`kjson`](https://github.com/pwall567/kjson) with applications that still use the Joda Time classes.

## Quick Start

To use this library, add the conversions required to a `JSONConfig` used within your application, as shown below:

```kotlin
    val config = JSONConfig {
        addJodaInstantFromJSON() // to deserialize org.joda.time.Instant
        addJodaInstantToJSON() // to serialize org.joda.time.Instant
        addJodaLocalTimeFromJSON() // to deserialize org.joda.time.LocalTime
        addJodaLocalTimeToJSON() // to serialize org.joda.time.LocalTime
        addJodaDateTimeFromJSON() // to deserialize org.joda.time.DateTime
        addJodaDateTimeToJSON() // to serialize org.joda.time.DateTime
    }
```

Alternatively, to include the entire set of conversions:

```kotlin
    val config = JSONConfig {
        combineAll(JodaTimeJSON.config)
    }
```

## More Documentation to Follow

This is the initial release of the library; further classes and more documentation will be added later.

## Dependency Specification

The latest version of the library is 0.1, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>io.kjson</groupId>
      <artifactId>kjson-joda-time</artifactId>
      <version>0.1</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'io.kjson:kjson-joda-time:0.1'
```
### Gradle (kts)
```kotlin
    implementation("io.kjson:kjson-joda-time:0.1")
```

Peter Wall

2023-02-05
