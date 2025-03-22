package devcon.map.data

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log

object DatabaseManager {

    private lateinit var dbHelper: DatabaseHelper

    fun init(context: Context) {
        dbHelper = DatabaseHelper(context)
        clearDatabase()
    }

    fun clearDatabase(): Int {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete(CafeContract.CafeEntry.TABLE_NAME, null, null)
        db.close()

        if (deletedRows == 0) {
            Log.e("DatabaseManager", "데이터 삭제 실패")
        } else {
            Log.d("DatabaseManager", "데이터 삭제 성공, 삭제된 행 수: $deletedRows")
        }

        return deletedRows
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

    fun getCafesByTitle(title: String): List<CafeDTO> {
        val db = dbHelper.readableDatabase
        val cafes = mutableListOf<CafeDTO>()
        val query = "SELECT * FROM ${CafeContract.CafeEntry.TABLE_NAME} WHERE ${CafeContract.CafeEntry.COLUMN_NAME_TITLE} LIKE ?"
        val cursor = db.rawQuery(query, arrayOf("%$title%"))
        Log.d("DB", "Rows returned: ${cursor.count}")

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val address = getString(getColumnIndexOrThrow(CafeContract.CafeEntry.COLUMN_NAME_ADDRESS))
                cafes.add(CafeDTO(id, title, address))
            }
            close()
        }

        db.close()
        return cafes
    }
}

