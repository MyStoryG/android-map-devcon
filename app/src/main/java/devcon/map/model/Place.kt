package devcon.map.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The place.
 *
 * @param id The unique ID of the place.
 * @param name The name of the place.
 * @param address The address of the place.
 * @param category The category of the place.
 * @param latitude The latitude of the place.
 * @param longitude The longitude of the place.
 */
@Parcelize
data class Place(
    val id: String,
    val name: String,
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
) : Parcelable
