package devcon.map.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        const val TABLE_NAME = "mytable"
        const val HISTORY_TABLE_NAME = "history"
        const val COLUMN_TITLE = "title"
        const val COLUMN_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT
            )
        """.trimIndent()

        val createHistoryTableQuery = """
            CREATE TABLE $HISTORY_TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
        db.execSQL(createHistoryTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN new_column TEXT")
            db.execSQL("ALTER TABLE $HISTORY_TABLE_NAME ADD COLUMN new_column TEXT")
        }
    }

    /** 중복 값 체크 안한 상태로 냅둬도 무방하려나...? **/
    private fun insert(data: SampleData, table: String): Long {
        val result = writableDatabase.insert(table, null, values(data))
        notifyDataChanged()
        return result
    }

    private fun read(table: String): List<SampleData> {
        val dataList = mutableListOf<SampleData>()
        val cursor: Cursor = readableDatabase.query(table, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))

                dataList.add(SampleData(id, title))
            }
        }

        cursor.close()
        return dataList
    }

    private fun update(data: SampleData, table: String): Int {
        val result = writableDatabase.update(
            table,
            values(data),
            "$COLUMN_ID = ?",
            selectionArgs(data.id)
        )
        notifyDataChanged()
        return result
    }

    /** @param id id of data to delete
     * @param table table to delete from **/
    private fun delete(id: Long, table: String): Int {
        val result = writableDatabase.delete(table, "$COLUMN_ID = ?", selectionArgs(id))
        notifyDataChanged()
        return result
    }

    private fun values(data: SampleData): ContentValues {
        return ContentValues().apply {
            put(COLUMN_ID, data.id)
            put(COLUMN_TITLE, data.title)
        }
    }

    private fun selectionArgs(id: Long): Array<String> {
        return arrayOf(id.toString())
    }

    fun insertData(data: SampleData) = insert(data, TABLE_NAME)
    fun insertHistory(data: SampleData) = insert(data, HISTORY_TABLE_NAME)

    fun readData() = read(TABLE_NAME)
    fun readHistory() = read(HISTORY_TABLE_NAME)

    fun updateData(data: SampleData) = update(data, TABLE_NAME)
    fun updateHistory(data: SampleData) = update(data, HISTORY_TABLE_NAME)

    fun deleteData(id: Long) = delete(id, TABLE_NAME)
    fun deleteHistory(id: Long) = delete(id, HISTORY_TABLE_NAME)

    //----------- Flow part -----------

    // A simple flag to indicate that data has changed
    private var dataChanged = false

    // Method to notify that data has changed
    @Synchronized
    private fun notifyDataChanged() {
        dataChanged = true
    }

    private fun getAsFlow(table: String): Flow<List<SampleData>> = callbackFlow {
        send(read(table))

        while (isActive) {
            if (dataChanged) {
                dataChanged = false
                send(read(table))
            }
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    fun getDataAsFlow() = getAsFlow(TABLE_NAME)
    fun getHistoryAsFlow() = getAsFlow(HISTORY_TABLE_NAME)
}