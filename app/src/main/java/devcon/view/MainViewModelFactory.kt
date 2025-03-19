package devcon.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import devcon.data.PlaceRepository

class MainViewModelFactory(
    private val repository: PlaceRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
