package devcon.view

import androidx.lifecycle.ViewModel
import devcon.core.ObservableData
import devcon.data.PlaceRepository
import devcon.data.SearchWordRepository
import devcon.domain.Place
import kotlin.random.Random

class MainViewModel(
    private val placeRepository: PlaceRepository,
    private val searchWordRepository: SearchWordRepository,
) : ViewModel() {
    val searchWordsObservableData = ObservableData<List<String>>(emptyList())
    val placeObservableData = ObservableData<List<Place>>(emptyList())

    init {
        notifySearchWordsChange()
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

        placeRepository.insertAll(dummyPlaces)
    }

    fun loadPlaces(searchText: String) {
        if (searchText.isBlank()) {
            placeObservableData.setValue(emptyList())
            return
        }

        val placeList = placeRepository.searchPlace(searchText)
        placeObservableData.setValue(placeList)
    }

    fun onClickPlace(placeName: String) {
        searchWordRepository.add(placeName)
        loadPlaces(placeName)

        notifySearchWordsChange()
    }

    fun onClickDeleteSearchWord(searchWord: String) {
        searchWordRepository.remove(searchWord)

        notifySearchWordsChange()
    }

    private fun notifySearchWordsChange() {
        searchWordRepository.getItems().let { searchWordsObservableData.setValue(it) }
    }
}
