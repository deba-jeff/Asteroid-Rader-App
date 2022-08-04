package ukponahiunsijeffery.example.asteroidraderapp

import ukponahiunsijeffery.example.asteroidraderapp.network.AsteroidProperty
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 *  Parse the String JSON result to an ArrayList of AsteroidProperty Objects
 */
fun parseAsteroidJsonStringToAsteroidProperty(rawJsonString: String, numberOfLoop: Int):
        ArrayList<AsteroidProperty> {

    val asteroidPropertyArrayList = ArrayList<AsteroidProperty>()

    val baseJsonObject = JSONObject(rawJsonString)
    val nearEarthJsonObject = baseJsonObject.getJSONObject("near_earth_objects")

    // Loops to get each Calendar dates Array name
    for (i in 0 until numberOfLoop) {

        val singleCalenderDateJsonArray = nearEarthJsonObject.getJSONArray(getNextSevenCalenderDates()[i])

        // Loops to get each Object in the Calendar date
        for (k in 0 until singleCalenderDateJsonArray.length()) {

            val calenderObject = singleCalenderDateJsonArray.getJSONObject(k)

            val id: String = calenderObject.getString("id")

            val name: String = calenderObject.getString("name")

            val absoluteMagnitude: Double = calenderObject.getDouble("absolute_magnitude_h")

            val estimatedDiameterJsonObject = calenderObject.getJSONObject("estimated_diameter")
            val kilometerJsonObject = estimatedDiameterJsonObject.getJSONObject("kilometers")
            val estimatedDiameter: Double = kilometerJsonObject.getDouble("estimated_diameter_max")

            val isPotentiallyHazardous: Boolean = calenderObject.getBoolean("is_potentially_hazardous_asteroid")

            val closeApproachDataArray = calenderObject.getJSONArray("close_approach_data")
            val closeApproachDataJsonObject = closeApproachDataArray.getJSONObject(0)
            val closeApproachDate: String = closeApproachDataJsonObject.getString("close_approach_date")

            val relativeVelocityJsonObject = closeApproachDataJsonObject.getJSONObject("relative_velocity")
            val relativeVelocity: String = relativeVelocityJsonObject.getString("kilometers_per_hour")

            val missDistanceObject = closeApproachDataJsonObject.getJSONObject("miss_distance")
            val distanceFromEarth: String = missDistanceObject.getString("kilometers")

            val asteroidProperty = AsteroidProperty(id, name, absoluteMagnitude, estimatedDiameter,
                isPotentiallyHazardous, closeApproachDate, relativeVelocity, distanceFromEarth)

            asteroidPropertyArrayList.add(asteroidProperty)
        }
    }

    return asteroidPropertyArrayList
}



/**
 *  Returns the next seven calender dates
 */
fun getNextSevenCalenderDates(): ArrayList<String> {

    val calenderDatesArrayList = ArrayList<String>()

    // Gets a calendar using the current time in the default time zone with the default FORMAT locale.
    val calendar = Calendar.getInstance()

    // Loops seven times to get an ArrayList of the next seven Calender dates
    for (i in 0..7) {

        // Returns a Date Object representing the Calendar's time value
        val currentTime = calendar.time

        // Constructs a date format
        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())

        // Formats the Date Object into a date String and adds the date String to the calenderDatesArrayList
        calenderDatesArrayList.add(dateFormat.format(currentTime))

        // Adds 1 day to the current time of the calendar
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return calenderDatesArrayList
}