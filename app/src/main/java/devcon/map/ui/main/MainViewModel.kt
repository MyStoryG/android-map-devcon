package devcon.map.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import devcon.map.data.CafeDTO

class MainViewModel : ViewModel() {

    private val _searchResult = MutableLiveData<List<CafeDTO>>()
    val searchResult: LiveData<List<CafeDTO>> get() = _searchResult

}