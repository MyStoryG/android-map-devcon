package devcon.map.repository

import android.content.Context
import devcon.map.data.MyDatabaseHelper
import devcon.map.data.SampleData
import kotlinx.coroutines.flow.Flow

class SampleRepository(context: Context) {
    private val dbHelper = MyDatabaseHelper(context)

    fun initData() {
        if (readData().isEmpty()) {
            for (i in 1..200) {
                insertData(SampleData(i.toLong(), "title $i"))
            }
        }
    }

    /** 전체 목록 **/
    fun dataFlow(): Flow<List<SampleData>> = dbHelper.getDataAsFlow()

    fun historyFlow(): Flow<List<SampleData>> = dbHelper.getHistoryAsFlow()

    fun insertData(data: SampleData) = dbHelper.insertData(data)

    fun insertHistory(data: SampleData) = dbHelper.insertHistory(data)

    fun readData(): List<SampleData> = dbHelper.readData()

    fun deleteData(id: Long) = dbHelper.deleteData(id)

    fun deleteHistory(id: Long) = dbHelper.deleteHistory(id)

    fun updateData(data: SampleData) = dbHelper.updateData(data)

}