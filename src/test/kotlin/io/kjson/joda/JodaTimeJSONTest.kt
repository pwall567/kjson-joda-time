/*
 * @(#) JodaTimeJSONTest.java
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

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.expect

import io.kjson.JSONConfig
import io.kjson.JSONException
import io.kjson.JSONString
import io.kjson.fromJSONValue
import io.kjson.joda.JodaTimeJSON.addJodaDateTimeFromJSON
import io.kjson.joda.JodaTimeJSON.addJodaDateTimeToJSON
import io.kjson.joda.JodaTimeJSON.addJodaInstantFromJSON
import io.kjson.joda.JodaTimeJSON.addJodaInstantToJSON
import io.kjson.joda.JodaTimeJSON.addJodaLocalTimeFromJSON
import io.kjson.joda.JodaTimeJSON.addJodaLocalTimeToJSON
import io.kjson.parseJSON
import io.kjson.stringifyJSON

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Instant
import org.joda.time.LocalTime

class JodaTimeJSONTest {

    @Test fun `should input Joda Time Instant`() {
        val config = JSONConfig {
            addJodaInstantFromJSON()
        }
        val json = JSONString("2023-02-03T10:20:45.367Z")
        val instant: Instant = json.fromJSONValue(config)
        expect(DateTime(2023, 2, 3, 10, 20, 45, 367, DateTimeZone.UTC).toInstant()) { instant }
    }

    @Test fun `should reject invalid Joda Time Instant`() {
        val config = JSONConfig {
            addJodaInstantFromJSON()
        }
        assertFailsWith<JSONException> { JSONString("wrong").fromJSONValue<Instant>(config) }.let {
            expect("Error in custom fromJSON mapping of org.joda.time.Instant") { it.message }
            expect("Node not correct type (Instant), was \"wrong\"") { it.cause?.message }
        }
    }

    @Test fun `should output Joda Time Instant`() {
        val config = JSONConfig {
            addJodaInstantToJSON()
        }
        val instant: Instant = DateTime(2023, 2, 3, 10, 20, 45, 367, DateTimeZone.UTC).toInstant()
        expect("\"2023-02-03T10:20:45.367Z\"") { instant.stringifyJSON(config) }
    }

    @Test fun `should input Joda Time LocalTime`() {
        val config = JSONConfig {
            addJodaLocalTimeFromJSON()
        }
        val json = JSONString("21:20:45.367")
        val localTime: LocalTime = json.fromJSONValue(config)
        expect(LocalTime(21, 20, 45, 367)) { localTime }
    }

    @Test fun `should output Joda Time LocalTime`() {
        val config = JSONConfig {
            addJodaLocalTimeToJSON()
        }
        val localTime = LocalTime(21, 20, 45, 367)
        expect("\"21:20:45.367\"") { localTime.stringifyJSON(config) }
    }

    @Test fun `should input Joda Time DateTime`() {
        val config = JSONConfig {
            addJodaDateTimeFromJSON()
        }
        val json = JSONString("2023-02-03T21:20:45.367+11:00")
        val dateTime: DateTime = json.fromJSONValue(config)
        expect(DateTime(2023, 2, 3, 21, 20, 45, 367, DateTimeZone.forOffsetHours(11))) { dateTime }
    }

    @Test fun `should output Joda Time DateTime`() {
        val config = JSONConfig {
            addJodaDateTimeToJSON()
        }
        val dateTime = DateTime(2023, 2, 3, 21, 20, 45, 367, DateTimeZone.forOffsetHours(11))
        expect("\"2023-02-03T21:20:45.367+11:00\"") { dateTime.stringifyJSON(config) }
    }

    @Test fun `should use combined config`() {
        val config = JSONConfig {
            combineAll(JodaTimeJSON.config)
        }
        val instant: Instant = JSONString("2023-02-03T10:20:45.367Z").fromJSONValue(config)
        expect(DateTime(2023, 2, 3, 10, 20, 45, 367, DateTimeZone.UTC).toInstant()) { instant }
        expect("\"2023-02-03T10:20:45.367Z\"") { instant.stringifyJSON(config)}
        val localTime: LocalTime = JSONString("21:20:45.367").fromJSONValue(config)
        expect(LocalTime(21, 20, 45, 367)) { localTime }
        expect("\"21:20:45.367\"") { localTime.stringifyJSON(config)}
        val dateTime: DateTime = "\"2023-02-03T21:20:45.367+11:00\"".parseJSON(config)!!
        expect(DateTime(2023, 2, 3, 21, 20, 45, 367, DateTimeZone.forOffsetHours(11))) { dateTime }
        expect("\"2023-02-03T21:20:45.367+11:00\"") { dateTime.stringifyJSON(config)}
    }

}
