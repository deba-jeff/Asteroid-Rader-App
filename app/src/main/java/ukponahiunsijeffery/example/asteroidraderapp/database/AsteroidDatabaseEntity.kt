package ukponahiunsijeffery.example.asteroidraderapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain

@Entity(tableName = "asteroid_table")
data class AsteroidDatabaseEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var absoluteMagnitude: Double,
    var estimatedDiameter: Double,
    var isPotentiallyHazardous: Boolean,
    var closeApproachDate: String,
    var relativeVelocity: String,
    var distanceFromEarth: String)



/**
 *  Converts an ArrayList of AsteroidDatabaseEntity to a List of AsteroidDomain
 */
fun List<AsteroidDatabaseEntity>.asDomainModel(): List<AsteroidDomain>{
    return map {
        AsteroidDomain(
            id = it.id,
            name = it.name,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            closeApproachDate = it.closeApproachDate,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth
        )
    }

}










