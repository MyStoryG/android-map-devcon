package devcon.map.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import devcon.map.database.KeywordContract.KeywordEntry
import devcon.map.database.PlaceContract.PlaceEntry

class DatabaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_KEYWORD_TABLE)
        db.execSQL(SQL_CREATE_PLACE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_KEYWORD_TABLE)
        db.execSQL(SQL_DELETE_PLACE_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DATABASE_NAME = "map_database.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_KEYWORD_TABLE =
            "CREATE TABLE ${KeywordEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${KeywordEntry.COLUMN_WORD} TEXT," +
                    "${KeywordEntry.COLUMN_SEARCHED_AT} INTEGER)"
        private const val SQL_DELETE_KEYWORD_TABLE =
            "DROP TABLE IF EXISTS ${KeywordEntry.TABLE_NAME}"

        private const val SQL_CREATE_PLACE_TABLE =
            "CREATE TABLE ${PlaceEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${PlaceEntry.COLUMN_NAME} TEXT," +
                    "${PlaceEntry.COLUMN_ADDRESS} TEXT," +
                    "${PlaceEntry.COLUMN_CATEGORY} TEXT," +
                    "${PlaceEntry.COLUMN_CREATED_AT} INTEGER)"
        private const val SQL_DELETE_PLACE_TABLE = "DROP TABLE IF EXISTS ${PlaceEntry.TABLE_NAME}"
    }
}
