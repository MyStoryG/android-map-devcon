package devcon.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import devcon.map.data.repository.UserPreferencesRepository
import devcon.map.model.Location
import kotlinx.coroutines.launch

class UserPreferencesViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    val initialSetupEvent = liveData {
        emit(userPreferencesRepository.fetchInitialPreferences())
    }

    fun updateLastKnownLocation(location: Location) {
        viewModelScope.launch { userPreferencesRepository.updateLastKnownLocation(location) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userPreferencesRepository =
                    (this[APPLICATION_KEY] as MapApplication).userPreferencesRepository
                UserPreferencesViewModel(userPreferencesRepository)
            }
        }
    }
}
