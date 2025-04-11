package devcon.map.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devcon.map.MapApplication
import devcon.map.data.repository.SearchRepository
import devcon.map.model.Keyword
import devcon.map.model.Location
import devcon.map.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val keywords = searchRepository.fetchSearchKeyword()
            _uiState.value = SearchUiState(
                keywords = keywords,
            )
        }
    }

    fun searchKeyword(keyword: Keyword) {
        viewModelScope.launch {
            val keywords = searchRepository.searchKeyword(keyword)
            _uiState.update { currentState ->
                currentState.copy(keywords = keywords)
            }
        }
    }

    fun deleteKeyword(keyword: Keyword) {
        viewModelScope.launch {
            val keywords = searchRepository.deleteKeyword(keyword)
            _uiState.update { currentState ->
                currentState.copy(keywords = keywords)
            }
        }
    }

    fun getSearchKeyword(page: Int, size: Int, query: String) {
        viewModelScope.launch {
            val response = searchRepository.getSearchKeyword(page, size, query)
            if (response.isSuccessful) {
                val places = response.body()?.documents?.map {
                    Place(
                        id = it.id,
                        name = it.placeName,
                        address = it.addressName,
                        category = it.categoryName,
                        location = Location(it.y.toDouble(), it.x.toDouble()),
                    )
                } ?: emptyList()

                _uiState.update { currentState ->
                    currentState.copy(places = places)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val searchRepository = (this[APPLICATION_KEY] as MapApplication).searchRepository
                SearchViewModel(searchRepository)
            }
        }
    }
}
