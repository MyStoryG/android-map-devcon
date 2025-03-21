package devcon.map.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import devcon.map.database.KeywordContract.KeywordEntry

class DatabaseHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DATABASE_NAME = "keywords.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${KeywordEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${KeywordEntry.COLUMN_WORD} TEXT," +
                    "${KeywordEntry.COLUMN_SEARCHED_AT} INTEGER)"
        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${KeywordEntry.TABLE_NAME}"
    }
}