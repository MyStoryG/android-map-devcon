package devcon.view

import androidx.lifecycle.ViewModel
import devcon.data.PlaceRepository
import devcon.domain.Place
import kotlin.random.Random

class MainViewModel(
    private val repository: PlaceRepository,
) : ViewModel() {
    private val _dummyData = mutableListOf<Place>()
    val dummyData: List<Place> = _dummyData

    init {
        testMethodForDummyData()
    }

    private fun testMethodForDummyData() {
        val dummyPlaces = mutableListOf<Place>()

        for (i in 1..300) {
            dummyPlaces.add(
                Place(
                    i.toLong(),
                    "Place $i",
                    "Type ${i % 5}",
                    "Address $i",
                    Random.nextDouble() * 100,
                    Random.nextDouble() * 100,
                ),
            )
        }

        repository.insertAll(dummyPlaces)
        loadPlaces()
    }

    private fun loadPlaces() {
        val placeList = repository.getPlaceList()
        _dummyData.addAll(placeList)
    }
}
