package devcon.map.ui

import devcon.map.model.Place

data class PlaceUiState(
    val places: List<Place> = emptyList(),
    val isEmpty: Boolean = true,
)
