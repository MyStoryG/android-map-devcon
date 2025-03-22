package devcon.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class PlaceDatabaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "places.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE ${PlaceContract.PlaceEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${PlaceContract.PlaceEntry.COLUMN_NAME} TEXT, " +
                "${PlaceContract.PlaceEntry.COLUMN_TYPE} TEXT, " +
                "${PlaceContract.PlaceEntry.COLUMN_ADDRESS} TEXT, " +
                "${PlaceContract.PlaceEntry.COLUMN_LATITUDE} REAL, " +
                "${PlaceContract.PlaceEntry.COLUMN_LONGITUDE} REAL" +
                ");"

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS ${PlaceContract.PlaceEntry.TABLE_NAME}")
        onCreate(db)
    }
}
