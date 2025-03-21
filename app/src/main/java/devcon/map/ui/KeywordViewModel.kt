package devcon.map.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devcon.map.MapApplication
import devcon.map.data.KeywordRepository
import devcon.map.model.Keyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeywordViewModel(
    private val keywordRepository: KeywordRepository,
) : ViewModel() {
    private val _keywords = MutableLiveData<List<Keyword>>()
    val keywords: LiveData<List<Keyword>>
        get() = _keywords

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadKeywords()
        }
    }

    private suspend fun loadKeywords() {
        keywordRepository.getKeywords().collect {
            _keywords.postValue(it)
        }
    }

    fun upsert(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.upsert(keyword).collect {
                loadKeywords()
            }
        }
    }

    fun delete(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.delete(keyword).collect {
                loadKeywords()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val keywordRepository = (this[APPLICATION_KEY] as MapApplication).keywordRepository
                KeywordViewModel(keywordRepository)
            }
        }
    }
}
