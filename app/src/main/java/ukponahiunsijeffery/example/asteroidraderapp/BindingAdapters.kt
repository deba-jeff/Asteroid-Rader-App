package ukponahiunsijeffery.example.asteroidraderapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import ukponahiunsijeffery.example.asteroidraderapp.network.PictureOfTheDay
import ukponahiunsijeffery.example.asteroidraderapp.overview.PictureOfTheDayStatus


/**
 *  Binds the Picture of the day ImageView
 */
@BindingAdapter("pictureOfTheDay")
fun bindPictureOfTheDay(pictureImageView: ImageView, pictureOfTheDayObject: PictureOfTheDay?){

    // Check for null and mediaType
    if (pictureOfTheDayObject?.mediaType == "image"){

        pictureImageView.visibility = View.VISIBLE

        // Check for null url
        pictureOfTheDayObject.url?.let {

            // Creates a Uri from the Url String
            val pictureUri = it.toUri().buildUpon().scheme("https").build()

            // Loads the Uri using coil into the ImageView
            pictureImageView.load(pictureUri){
                placeholder(R.drawable.loading_animation)
            }

            // Picture of the day content description
            pictureImageView.contentDescription = pictureImageView.context.getString(
                R.string.picture_of_the_day_content_description, pictureOfTheDayObject.title)
        }

    }

    // Hides the ImageView
    else{
        pictureImageView.visibility = View.GONE
    }

}


/**
 *  Binds the overview name TextView
 */
@BindingAdapter("overviewNameText")
fun bindOverviewName(overviewNameTextView: TextView, overviewNameString: String){
    overviewNameTextView.text = overviewNameString
}


/**
 *  Binds the overview date TextView
 */
@BindingAdapter("overviewDateText")
fun bindOverviewDate(overviewDateTextView: TextView, overviewDateString: String){
    overviewDateTextView.text = overviewDateString
}


/**
 *  Binds the overview potentially hazardous ImageView
 */
@BindingAdapter("overviewPotentiallyHazardousImage")
fun bindOverviewPotentiallyHazardous(overViewPotentiallyHazardousImageView: ImageView,
                                     overViewPotentiallyHazardousBoolean: Boolean){

    if (overViewPotentiallyHazardousBoolean){
        overViewPotentiallyHazardousImageView.setImageResource(R.drawable.ic_status_potentially_hazardous)

        // Potentially hazardous Asteroid icon content description
        overViewPotentiallyHazardousImageView.contentDescription = overViewPotentiallyHazardousImageView.context.
        getString(R.string.potentially_hazardous_asteroid_icon)
    }
    else{
        overViewPotentiallyHazardousImageView.setImageResource(R.drawable.ic_status_normal)

        // Not hazardous Asteroid icon content description
        overViewPotentiallyHazardousImageView.contentDescription = overViewPotentiallyHazardousImageView.context.
        getString(R.string.not_hazardous_asteroid_icon)
    }

}


/**
 *  Binds the status of the Picture of the day ImageView
 */
@BindingAdapter("pictureOfTheDayStatusImageView")
fun bindPictureOfTheDayStatusImageView(pictureStatusImageView: ImageView, currentStatus: PictureOfTheDayStatus?) {
    when (currentStatus) {
        PictureOfTheDayStatus.LOADING -> {
            pictureStatusImageView.visibility = View.VISIBLE
            pictureStatusImageView.setImageResource(R.drawable.loading_animation)
        }

        PictureOfTheDayStatus.DONE -> {
            pictureStatusImageView.visibility = View.GONE
        }

        PictureOfTheDayStatus.ERROR -> {
            pictureStatusImageView.visibility = View.GONE
        }
    }

}


/**
 *  Binds the status of the Picture of the day TextView
 */
@BindingAdapter("pictureOfTheDayStatusTextView")
fun bindPictureOfTheDayStatusTextView(pictureStatusTextView: TextView, currentStatus: PictureOfTheDayStatus?) {

    // Returns the context the Textview is running in
    val context = pictureStatusTextView.context

    when (currentStatus) {
        PictureOfTheDayStatus.LOADING -> {
            pictureStatusTextView.visibility = View.GONE
        }

        PictureOfTheDayStatus.DONE -> {
            pictureStatusTextView.visibility = View.GONE
        }

        PictureOfTheDayStatus.ERROR -> {
            pictureStatusTextView.visibility = View.VISIBLE
            pictureStatusTextView.text = context.getString(R.string.picture_of_the_day_loading_error_message)
        }
    }

}


/**
 *  Binds the Picture of the day heading TextView
 */
@BindingAdapter("pictureOfTheDayHeadingTextView")
fun bindPictureOfTheDayHeadingTextView(pictureHeadingTextView: TextView, currentStatus: PictureOfTheDayStatus?) {

    // Returns the context the Textview is running in
    val context = pictureHeadingTextView.context

    when (currentStatus) {
        PictureOfTheDayStatus.LOADING -> {
            pictureHeadingTextView.visibility = View.GONE
        }

        PictureOfTheDayStatus.DONE -> {
            pictureHeadingTextView.visibility = View.VISIBLE
            pictureHeadingTextView.text = context.getString(R.string.picture_of_the_day_heading_text)
        }

        PictureOfTheDayStatus.ERROR -> {
            pictureHeadingTextView.visibility = View.GONE
        }
    }

}


/**
 *  Binds the details potentially hazardous ImageView
 */
@BindingAdapter("detailsPotentiallyHazardousImage")
fun bindDetailsPotentiallyHazardous(detailsPotentiallyHazardousImageView: ImageView,
                                     detailsPotentiallyHazardousBoolean: Boolean){

    if (detailsPotentiallyHazardousBoolean){
        detailsPotentiallyHazardousImageView.setImageResource(R.drawable.asteroid_hazardous)

        // Potentially hazardous Asteroid ImageView content description
        detailsPotentiallyHazardousImageView.contentDescription = detailsPotentiallyHazardousImageView.context.
        getString(R.string.potentially_hazardous_asteroid_image)
    }
    else{
        detailsPotentiallyHazardousImageView.setImageResource(R.drawable.asteroid_safe)

        // Not hazardous Asteroid ImageView content description
        detailsPotentiallyHazardousImageView.contentDescription = detailsPotentiallyHazardousImageView.context.
        getString(R.string.not_hazardous_asteroid_image)
    }
}


/**
 *  Binds the details close approach date TextView
 */
@BindingAdapter("detailsCloseApproachDateString")
fun bindDetailsCloseApproachDate(detailsCloseApproachDateTextView: TextView,
                                 detailsCloseApproachDateString: String) {

    detailsCloseApproachDateTextView.text = detailsCloseApproachDateString
}


/**
 *  Binds the details absolute magnitude TextView
 */
@BindingAdapter("detailsAbsoluteMagnitudeDouble")
fun bindDetailsAbsoluteMagnitude(detailsAbsoluteMagnitudeTextView: TextView,
                                 detailsAbsoluteMagnitudeDouble: Double) {

    // Returns the context the Textview is running in
    val context = detailsAbsoluteMagnitudeTextView.context

    detailsAbsoluteMagnitudeTextView.text = String.format(
        context.getString(
            R.string.absolute_magnitude_format
        ), detailsAbsoluteMagnitudeDouble)
}


/**
 *  Binds the details estimated diameter TextView
 */
@BindingAdapter("detailsEstimatedDiameterDouble")
fun bindDetailsEstimatedDiameter(detailsEstimatedDiameterTextView: TextView,
                                 detailsEstimatedDiameterDouble: Double) {

    // Returns the context the Textview is running in
    val context = detailsEstimatedDiameterTextView.context

    detailsEstimatedDiameterTextView.text = String.format(context.getString(
        R.string.estimated_diameter_format), detailsEstimatedDiameterDouble)
}


/**
 *  Binds the details relative velocity TextView
 */
@BindingAdapter("detailsRelativeVelocityString")
fun bindDetailsRelativeVelocity(detailsRelativeVelocityTextView: TextView,
                                detailsRelativeVelocityString: String) {

    // Returns the context the Textview is running in
    val context = detailsRelativeVelocityTextView.context

    detailsRelativeVelocityTextView.text = String.format(context.getString(
        R.string.relative_velocity_format), detailsRelativeVelocityString)
}


/**
 *  Binds the details distance from the earth TextView
 */
@BindingAdapter("detailsDistanceFromEarthString")
fun bindDetailsDistanceFromEarth(detailsDistanceFromEarthTextView: TextView,
                                detailsDistanceFromEarthString: String) {

    // Returns the context the Textview is running in
    val context = detailsDistanceFromEarthTextView.context

    detailsDistanceFromEarthTextView.text = String.format(context.getString(
        R.string.distance_from_earth_format), detailsDistanceFromEarthString)
}

