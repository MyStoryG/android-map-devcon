package devcon.map.model

/**
 * The place.
 *
 * @param id The unique ID for a row.
 * @param name The name of the place.
 * @param address The address of the place.
 * @param category The category of the place.
 * @param createdAt The time when the place was created.
 */
data class Place(
    val id: Long = 0,
    val name: String,
    val address: String,
    val category: String,
    val createdAt: Long = System.currentTimeMillis(),
)
