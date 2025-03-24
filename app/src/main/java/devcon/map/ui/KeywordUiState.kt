package devcon.map.ui

import devcon.map.model.Keyword

data class KeywordUiState(
    val keywords: List<Keyword> = emptyList(),
    val isEmpty: Boolean = true,
)
