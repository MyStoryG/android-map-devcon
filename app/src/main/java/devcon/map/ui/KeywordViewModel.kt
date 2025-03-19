package devcon.map.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import devcon.map.data.KeywordRepository
import devcon.map.model.Keyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeywordViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository = KeywordRepository(application)

    private val _keywords = MutableLiveData<List<Keyword>>()
    val keywords: LiveData<List<Keyword>>
        get() = _keywords

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadKeywords()
        }
    }

    private suspend fun loadKeywords() {
        repository.getKeywords().collect {
            _keywords.postValue(it)
        }
    }

    fun upsert(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsert(keyword).collect {
                loadKeywords()
            }
        }
    }

    fun delete(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(keyword).collect {
                loadKeywords()
            }
        }
    }
}
