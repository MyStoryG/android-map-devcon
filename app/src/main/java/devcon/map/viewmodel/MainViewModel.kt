package devcon.map.viewmodel

import androidx.lifecycle.ViewModel
import devcon.map.repository.SampleRepository

class MainViewModel(private val sampleRepository: SampleRepository) : ViewModel() {

    fun onInit() {
        sampleRepository.initData()
    }
}