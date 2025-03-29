package devcon.map.ui

import devcon.map.model.Keyword
import devcon.map.model.Place

data class SearchUiState(
    val keywords: List<Keyword> = emptyList(),
    val places: List<Place> = emptyList(),
)
