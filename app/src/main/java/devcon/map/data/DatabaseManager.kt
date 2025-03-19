package devcon.map.data

import android.content.ContentValues
import android.content.Context
import android.util.Log

object DatabaseManager {

    private lateinit var dbHelper: DatabaseHelper

    fun init(context: Context) {
        dbHelper = DatabaseHelper(context)
    }

    fun insert(title: String, address: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CafeContract.CafeEntry.COLUMN_NAME_TITLE, title)
            put(CafeContract.CafeEntry.COLUMN_NAME_ADDRESS, address)
        }

        val newRowId = db.insert(CafeContract.CafeEntry.TABLE_NAME, null, values)
        db.close()

        if (newRowId == -1L) {
            Log.e("DatabaseManager", "데이터 삽입 실패")
        } else {
            Log.d("DatabaseManager", "데이터 삽입 성공, ID: $newRowId")
        }

        return newRowId
    }
}

