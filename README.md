# kjson-joda-time

[![Build Status](https://travis-ci.com/pwall567/kjson-joda-time.svg?branch=main)](https://app.travis-ci.com/github/pwall567/kjson-joda-time)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v1.7.21&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v1.7.21)
[![Maven Central](https://img.shields.io/maven-central/v/io.kjson/kjson-joda-time?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.kjson%22%20AND%20a:%22kjson-joda-time%22)

Joda Time Adapters for [`kjson`](https://github.com/pwall567/kjson)

## Background

Prior to the release of Java 1.8, the [Joda Time](https://www.joda.org/joda-time/) library provided data and time
implementations superior to those available in the standard JDK.
It has now become redundant, following the release of the `java.time.xxx` classes in JDK 1.8, but many applications
still use the Joda Time libraries, and it is likely to be some time before all those older applications are converted or
replaced.

The `kjson-joda-time` library provides custom serialization and deserialization classes to allow the use of the
[`kjson`](https://github.com/pwall567/kjson) with applications that still use the Joda Time classes.

## Quick Start

To use this library, add the conversions required to a `JSONConfig` within your application, as shown below:

```kotlin
    val config = JSONConfig {
        addJodaInstantFromJSON() // to deserialize org.joda.time.Instant
        addJodaInstantToJSON() // to serialize org.joda.time.Instant
        addJodaLocalTimeFromJSON() // to deserialize org.joda.time.LocalTime
        addJodaLocalTimeToJSON() // to serialize org.joda.time.LocalTime
        addJodaLocalDateFromJSON() // to deserialize org.joda.time.LocalDate
        addJodaLocalDateToJSON() // to serialize org.joda.time.LocalDate
        addJodaLocalDateTimeFromJSON() // to deserialize org.joda.time.LocalDateTime
        addJodaLocalDateTimeToJSON() // to serialize org.joda.time.LocalDateTime
        addJodaDateTimeFromJSON() // to deserialize org.joda.time.DateTime
        addJodaDateTimeToJSON() // to serialize org.joda.time.DateTime
        addJodaYearMonthFromJSON() // to deserialize org.joda.time.YearMonth
        addJodaYearMonthToJSON() // to serialize org.joda.time.YearMonth
        addJodaMonthDayFromJSON() // to deserialize org.joda.time.MonthDay
        addJodaMonthDayToJSON() // to serialize org.joda.time.MonthDay
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

The latest version of the library is 0.2, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>io.kjson</groupId>
      <artifactId>kjson-joda-time</artifactId>
      <version>0.2</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'io.kjson:kjson-joda-time:0.2'
```
### Gradle (kts)
```kotlin
    implementation("io.kjson:kjson-joda-time:0.2")
```

Peter Wall

2023-03-22
