package devcon.map.restapi

data class KeywordSearchResponse(
    val documents: List<KeywordDocument>,
    val meta: Meta
)
