package devcon.map.ui

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KeywordViewModel(
    private val keywordRepository: KeywordRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(KeywordUiState())
    val uiState: StateFlow<KeywordUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.getKeywords().collect { keywords ->
                _uiState.value = KeywordUiState(
                    keywords = keywords,
                    isEmpty = keywords.isEmpty(),
                )
            }
        }
    }

    fun upsert(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.upsert(keyword).collect {
                _uiState.update { currentState ->
                    val keywords = currentState.keywords.toMutableList().apply {
                        removeIf { it.word == keyword.word }
                        add(keyword)
                    }

                    KeywordUiState(
                        keywords = keywords,
                        isEmpty = keywords.isEmpty(),
                    )
                }
            }
        }
    }

    fun delete(keyword: Keyword) {
        viewModelScope.launch(Dispatchers.IO) {
            keywordRepository.delete(keyword).collect {
                _uiState.update { currentState ->
                    val keywords = currentState.keywords - keyword

                    KeywordUiState(
                        keywords = keywords,
                        isEmpty = keywords.isEmpty(),
                    )
                }
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
