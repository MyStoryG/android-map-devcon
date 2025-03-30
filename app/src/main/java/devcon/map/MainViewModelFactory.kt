package devcon.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import devcon.map.repository.HistoryRepository
import devcon.map.repository.RetrofitRepository
import devcon.map.viewmodel.MainViewModel

class MainViewModelFactory(
    private val historyRepository: HistoryRepository,
    private val retrofitRepository: RetrofitRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(historyRepository, retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}