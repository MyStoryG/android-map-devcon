package devcon.map.repository

import android.content.Context
import devcon.map.data.MyDatabaseHelper
import devcon.map.restapi.KeywordDocument
import kotlinx.coroutines.flow.Flow

class HistoryRepository(context: Context) {
    private val dbHelper = MyDatabaseHelper(context)

    fun historyFlow(): Flow<List<KeywordDocument>> = dbHelper.getHistoryAsFlow()

    fun insertHistory(data: KeywordDocument) = dbHelper.insertHistory(data)

    fun deleteHistory(placeName: String, address: String) =
        dbHelper.deleteHistory(placeName, address)
}