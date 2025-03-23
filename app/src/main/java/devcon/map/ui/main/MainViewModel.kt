package devcon.map.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import devcon.map.data.model.CafeDTO
import devcon.map.data.repository.CafeRepository

class MainViewModel(private val cafeRepository: CafeRepository) : ViewModel() {

    private val _searchResult = MutableLiveData<List<CafeDTO>>()
    val searchResult: LiveData<List<CafeDTO>> get() = _searchResult

    private val _savedSearch = MutableLiveData<Set<String>>()
    val savedSearch: LiveData<Set<String>> get() = _savedSearch

    fun runSearchTitle(title: String) {
        _searchResult.value = cafeRepository.getCafesByTitle(title)
    }

    fun addSavedSearch(title: String) {
        val currentSet = _savedSearch.value ?: emptySet()

        val updatedSet = currentSet.toMutableSet().apply {
            add(title)
        }

        _savedSearch.value = updatedSet
    }

}