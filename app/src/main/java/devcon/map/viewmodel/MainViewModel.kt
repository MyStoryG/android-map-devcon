package devcon.map.viewmodel

import androidx.lifecycle.ViewModel
import devcon.map.data.SampleData
import devcon.map.repository.SampleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel(private val sampleRepository: SampleRepository) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    /** sort 기준을 다듬을 필요가 있을듯? **/
    val historyFlow = sampleRepository.historyFlow()

    val contentFlow: Flow<List<SampleData>> = combine(
        sampleRepository.dataFlow(),
        searchFlow
    ) { data, search ->
        matchDataFilter(data, search)
    }

    fun afterChanged(text: String) {
        searchFlow.value = text
    }

    /** db 초기 값 setter **/
    fun checkDB() {
        CoroutineScope(Dispatchers.IO).launch {
            sampleRepository.initData()
        }
    }

    fun onDeleteHistory(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            sampleRepository.deleteHistory(id)
        }
    }

    fun onSelectItem(data: SampleData) {
        CoroutineScope(Dispatchers.IO).launch {
            sampleRepository.insertHistory(data)
        }
    }

    /** 검색 데이터 필터링 **/
    private fun matchDataFilter(data: List<SampleData>, search: String): List<SampleData> {
        if (search.isEmpty()) {
            return data
        } else {
            return data.filter {
                it.title.contains(search)
            }
        }
    }
}