package devcon.map.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devcon.map.MapApplication
import devcon.map.data.PlaceRepository
import devcon.map.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaceViewModel(
    private val placeRepository: PlaceRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState: StateFlow<PlaceUiState> = _uiState.asStateFlow()

    init {
        createDummyData()
    }

    private fun createDummyData() {
        val dummy = listOf(
            Place(name = "Google", address = "Mountain View, CA", category = "Company"),
            Place(name = "Microsoft", address = "Redmond, WA", category = "Company"),
            Place(name = "Facebook", address = "Menlo Park, CA", category = "Company"),
            Place(name = "Apple", address = "Cupertino, CA", category = "Company"),
            Place(name = "Amazon", address = "Seattle, WA", category = "Company"),
            Place(name = "Samsung", address = "Seoul, Korea", category = "Company"),
            Place(name = "Kakao", address = "Seoul, Korea", category = "Company"),
            Place(name = "Naver", address = "Seoul, Korea", category = "Company"),
            Place(name = "Starbucks", address = "Seoul, Korea", category = "Cafe"),
            Place(name = "McDonald's", address = "Seoul, Korea", category = "Fest food"),
            Place(name = "Pizza Hut", address = "Seoul, Korea", category = "Fest food"),
            Place(name = "Subway", address = "Seoul, Korea", category = "Fest food"),
            Place(name = "KFC", address = "Seoul, Korea", category = "Fest food"),
        )

        insertAll(dummy)
    }

    private fun insertAll(places: List<Place>) {
        viewModelScope.launch(Dispatchers.IO) {
            // NOP: You can implement the action necessary when a place is added.
            placeRepository.insertAll(places).collect()
        }
    }

    fun getPlacesByName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (value.isNotEmpty()) {
                true -> placeRepository.getPlacesByName(value).collect { places ->
                    _uiState.value = PlaceUiState(places = places, isEmpty = places.isEmpty())
                }

                false -> _uiState.value = PlaceUiState(places = emptyList(), isEmpty = true)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val placeRepository = (this[APPLICATION_KEY] as MapApplication).placeRepository
                PlaceViewModel(placeRepository)
            }
        }
    }
}
