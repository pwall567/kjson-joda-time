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
import org.joda.time.LocalTime

object JodaTimeJSON {

    private val localTimeRegex = Regex("^([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9](.[0-9]+)?)?$")

    val config = JSONConfig {
        addJodaInstantFromJSON()
        addJodaInstantToJSON()
        addJodaLocalTimeFromJSON()
        addJodaLocalTimeToJSON()
        addJodaDateTimeFromJSON()
        addJodaDateTimeToJSON()
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

    fun JSONConfig.addJodaDateTimeFromJSON() {
        fromJSONString<DateTime> {
            if (!JSONValidation.isDateTime(it.value))
                throw JSONIncorrectTypeException(target = "DateTime", value = it)
            DateTime.parse(it.value)
        }
    }

    fun JSONConfig.addJodaDateTimeToJSON() {
        toJSONString<DateTime>()
    }

}
