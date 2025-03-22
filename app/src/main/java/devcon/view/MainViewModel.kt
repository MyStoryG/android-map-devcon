package devcon.view

import androidx.lifecycle.ViewModel
import devcon.core.ObservableData
import devcon.data.PlaceRepository
import devcon.domain.Place

class MainViewModel(
    private val repository: PlaceRepository,
) : ViewModel() {
    val placeObservableData = ObservableData<List<Place>>(emptyList())

    fun loadPlaces(searchText: String) {
        if (searchText.isBlank()) {
            placeObservableData.setValue(emptyList())
            return
        }

        val placeList = repository.searchPlace(searchText)
        placeObservableData.setValue(placeList)
    }

    fun onClickPlace(it: Place) {
        loadPlaces(it.name)
    }
}
