/*
 * @(#) JodaTimeJSON.java
 *
 * kjson-joda-time  Joda Time Adapters for kjson
 * Copyright (c) 2023 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.kjson.joda

import io.kjson.JSONConfig
import io.kjson.JSONIncorrectTypeException
import net.pwall.json.validation.JSONValidation

import org.joda.time.DateTime
import org.joda.time.Instant
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.MonthDay
import org.joda.time.YearMonth

/**
 * Joda Time serialization and deserialization for `kjson`.
 *
 * @author  Peter Wall
 */
object JodaTimeJSON {

    private val localTimeRegex = Regex("^([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9](.[0-9]+)?)?$")
    private val yearMonthRegex = Regex("^[0-9]{4}-(0[1-9]|1[0-2])$")
    private val monthDayRegex = Regex("^--(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")

    val config = JSONConfig {
        addJodaInstantFromJSON()
        addJodaInstantToJSON()
        addJodaLocalTimeFromJSON()
        addJodaLocalTimeToJSON()
        addJodaLocalDateFromJSON()
        addJodaLocalDateToJSON()
        addJodaLocalDateTimeFromJSON()
        addJodaLocalDateTimeToJSON()
        addJodaDateTimeFromJSON()
        addJodaDateTimeToJSON()
        addJodaYearMonthFromJSON()
        addJodaYearMonthToJSON()
        addJodaMonthDayFromJSON()
        addJodaMonthDayToJSON()
    }

    fun JSONConfig.addJodaInstantFromJSON() {
        fromJSONString<Instant> {
            val string = it.value
            if (!JSONValidation.isDateTime(string) || !string.endsWith('Z'))
                throw JSONIncorrectTypeException(target = "Instant", value = it)
            Instant.parse(string)
        }
    }

    fun JSONConfig.addJodaInstantToJSON() {
        toJSONString<Instant>()
    }

    fun JSONConfig.addJodaLocalTimeFromJSON() {
        fromJSONString<LocalTime> {
            val string = it.value
            if (!localTimeRegex.containsMatchIn(string))
                throw JSONIncorrectTypeException(target = "LocalTime", value = it)
            LocalTime.parse(string)
        }
    }

    fun JSONConfig.addJodaLocalTimeToJSON() {
        toJSONString<LocalTime>()
    }

    fun JSONConfig.addJodaLocalDateFromJSON() {
        fromJSONString<LocalDate> {
            val string = it.value
            if (!JSONValidation.isDate(string))
                throw JSONIncorrectTypeException(target = "LocalDate", value = it)
            LocalDate.parse(string)
        }
    }

    fun JSONConfig.addJodaLocalDateToJSON() {
        toJSONString<LocalDate>()
    }

    fun JSONConfig.addJodaLocalDateTimeFromJSON() {
        fromJSONString<LocalDateTime> {
            val string = it.value
            if (!JSONValidation.isDate(string.take(10)) || string[10] != 'T' ||
                    !localTimeRegex.containsMatchIn(string.drop(11)))
                throw JSONIncorrectTypeException(target = "LocalDateTime", value = it)
            LocalDateTime.parse(string)
        }
    }

    fun JSONConfig.addJodaLocalDateTimeToJSON() {
        toJSONString<LocalDateTime>()
    }

    fun JSONConfig.addJodaDateTimeFromJSON() {
        fromJSONString<DateTime> {
            val string = it.value
            if (!JSONValidation.isDateTime(string))
                throw JSONIncorrectTypeException(target = "DateTime", value = it)
            DateTime.parse(string)
        }
    }

    fun JSONConfig.addJodaDateTimeToJSON() {
        toJSONString<DateTime>()
    }

    fun JSONConfig.addJodaYearMonthFromJSON() {
        fromJSONString<YearMonth> {
            val string = it.value
            if (!yearMonthRegex.containsMatchIn(string))
                throw JSONIncorrectTypeException(target = "YearMonth", value = it)
            YearMonth.parse(string)
        }
    }

    fun JSONConfig.addJodaYearMonthToJSON() {
        toJSONString<YearMonth>()
    }

    fun JSONConfig.addJodaMonthDayFromJSON() {
        fromJSONString<MonthDay> {
            val string = it.value
            if (!monthDayRegex.containsMatchIn(string))
                throw JSONIncorrectTypeException(target = "MonthDay", value = it)
            MonthDay.parse(string)
        }
    }

    fun JSONConfig.addJodaMonthDayToJSON() {
        toJSONString<MonthDay>()
    }

}
