package devcon.map.model

/**
 * The search keyword.
 *
 * @param id The unique ID for a row.
 * @param word The keyword.
 * @param searchedAt The time when the keyword was searched.
 */
data class Keyword(
    val id: Long = 0,
    val word: String,
    val searchedAt: Long = System.currentTimeMillis(),
)
