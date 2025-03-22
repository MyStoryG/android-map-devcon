package devcon.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import devcon.data.PlaceRepository
import devcon.data.SearchWordRepository

class MainViewModelFactory(
    private val placeRepository: PlaceRepository,
    private val searchWordRepository: SearchWordRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(placeRepository, searchWordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
