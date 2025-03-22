package devcon.domain

data class Place(
    val id: Long,
    val name: String,
    val type: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
