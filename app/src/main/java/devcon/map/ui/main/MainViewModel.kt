package devcon.map.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import devcon.map.data.model.CafeDTO
import devcon.map.data.repository.CafeRepository

class MainViewModel(private val cafeRepository: CafeRepository) : ViewModel() {

    private val _searchResult = MutableLiveData<List<CafeDTO>>()
    val searchResult: LiveData<List<CafeDTO>> get() = _searchResult

    fun runSearchTitle(title: String) {
        _searchResult.value = cafeRepository.getCafesByTitle(title)
    }

}