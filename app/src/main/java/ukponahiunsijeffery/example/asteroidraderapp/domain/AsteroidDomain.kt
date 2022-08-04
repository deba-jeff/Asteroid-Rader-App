package ukponahiunsijeffery.example.asteroidraderapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  Domain Object
 */
@Parcelize
data class AsteroidDomain(
    val id: String,
    val name: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val isPotentiallyHazardous: Boolean,
    val closeApproachDate: String,
    val relativeVelocity: String,
    val distanceFromEarth: String): Parcelable
