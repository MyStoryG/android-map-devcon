package devcon.map.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import devcon.map.data.repository.CafeRepository

class MainViewModelFactory(private val repository: CafeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("잘못된 viewmodel 입니다.")
    }
}