package devcon.map.model

/**
 * The place.
 *
 * @param id The unique ID of the place.
 * @param name The name of the place.
 * @param address The address of the place.
 * @param category The category of the place.
 */
data class Place(
    val id: String,
    val name: String,
    val address: String,
    val category: String,
)
