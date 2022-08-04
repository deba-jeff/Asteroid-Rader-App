package ukponahiunsijeffery.example.asteroidraderapp.network

import ukponahiunsijeffery.example.asteroidraderapp.database.AsteroidDatabaseEntity


/**
 *  Network Asteroid Object to hold the result from the Network request
 */
data class AsteroidProperty(
    val id: String,
    val name: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val isPotentiallyHazardous: Boolean,
    val closeApproachDate: String,
    val relativeVelocity: String,
    val distanceFromEarth: String)


/**
 *  Converts an ArrayList of AsteroidProperty to an Array of AsteroidDatabaseEntity
 */
fun ArrayList<AsteroidProperty>.asDatabaseModel(): Array<AsteroidDatabaseEntity>{
    return map {
        AsteroidDatabaseEntity(
            id = it.id,
            name = it.name,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            closeApproachDate = it.closeApproachDate,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth
        )
    }.toTypedArray()
}

