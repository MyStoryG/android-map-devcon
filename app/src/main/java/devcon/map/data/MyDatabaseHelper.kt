package devcon.map.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import devcon.map.restapi.KeywordDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
        const val HISTORY_TABLE_NAME = "history"

        const val PLACE_NAME = "place_name"
        const val ADDRESS_NAME = "address_name"
        const val ROAD_ADDRESS_NAME = "road_address_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createHistoryTableQuery = """
            CREATE TABLE $HISTORY_TABLE_NAME (
                $PLACE_NAME TEXT,
                $ADDRESS_NAME TEXT,
                $ROAD_ADDRESS_NAME TEXT
            )
        """.trimIndent()

        db.execSQL(createHistoryTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $HISTORY_TABLE_NAME ADD COLUMN new_column TEXT")
        }
    }

    /** 중복 값 체크 안한 상태로 냅둬도 무방하려나...? **/
    private fun insert(data: KeywordDocument, table: String): Long {
        val result = writableDatabase.insert(table, null, values(data))
        notifyDataChanged()
        return result
    }

    private fun read(table: String): List<KeywordDocument> {
        val dataList = mutableListOf<KeywordDocument>()
        val cursor: Cursor = readableDatabase.query(table, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val placeName = getString(getColumnIndexOrThrow(PLACE_NAME))
                val addressName = getString(getColumnIndexOrThrow(ADDRESS_NAME))
                val roadAddressName = getString(getColumnIndexOrThrow(ROAD_ADDRESS_NAME))

                dataList.add(
                    KeywordDocument(
                        placeName,
                        addressName,
                        roadAddressName
                    )
                )
            }
        }

        cursor.close()
        return dataList
    }

    private fun delete(placeName: String, addressName: String, table: String): Int {
        val args = "$PLACE_NAME = ? AND $ADDRESS_NAME = ?"
        val result = writableDatabase.delete(table, args, arrayOf(placeName, addressName))
        notifyDataChanged()
        return result
    }

    private fun values(data: KeywordDocument): ContentValues {
        return ContentValues().apply {
            put(PLACE_NAME, data.place_name)
            put(ADDRESS_NAME, data.address_name)
            put(ROAD_ADDRESS_NAME, data.road_address_name)
        }
    }

    fun insertHistory(data: KeywordDocument) = insert(data, HISTORY_TABLE_NAME)
    fun deleteHistory(placeName: String, addressName: String) =
        delete(placeName, addressName, HISTORY_TABLE_NAME)

    //----------- Flow part -----------

    // A simple flag to indicate that data has changed
    private var dataChanged = false

    // Method to notify that data has changed
    @Synchronized
    private fun notifyDataChanged() {
        dataChanged = true
    }

    private fun getAsFlow(table: String): Flow<List<KeywordDocument>> = callbackFlow {
        send(read(table))

        while (isActive) {
            if (dataChanged) {
                dataChanged = false
                send(read(table))
            }
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    fun getHistoryAsFlow() = getAsFlow(HISTORY_TABLE_NAME)
}