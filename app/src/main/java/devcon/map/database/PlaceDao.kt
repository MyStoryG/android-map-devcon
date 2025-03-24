package devcon.map.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.database.sqlite.transaction
import devcon.map.model.Place
import devcon.map.database.PlaceContract.PlaceEntry

class PlaceDao(private val database: SQLiteDatabase) {
    private fun insert(place: Place): Long {
        val values = ContentValues().apply {
            put(PlaceEntry.COLUMN_NAME, place.name)
            put(PlaceEntry.COLUMN_ADDRESS, place.address)
            put(PlaceEntry.COLUMN_CATEGORY, place.category)
            put(PlaceEntry.COLUMN_CREATED_AT, place.createdAt)
        }

        return database.insert(PlaceEntry.TABLE_NAME, null, values)
    }

    private fun upsert(place: Place) {
        val values = ContentValues().apply {
            put(PlaceEntry.COLUMN_NAME, place.name)
            put(PlaceEntry.COLUMN_ADDRESS, place.address)
            put(PlaceEntry.COLUMN_CATEGORY, place.category)
            put(PlaceEntry.COLUMN_CREATED_AT, place.createdAt)
        }

        val whereClause = "${PlaceEntry.COLUMN_NAME} = ? AND ${PlaceEntry.COLUMN_ADDRESS} = ?"
        val whereArgs = arrayOf(place.name, place.address)

        val updatedRows = database.update(
            PlaceEntry.TABLE_NAME,
            values,
            whereClause,
            whereArgs
        )

        if (updatedRows == 0) {
            insert(place)
        }
    }

    fun insertAll(places: List<Place>): List<Place> {
        database.transaction {
            places.forEach { place -> upsert(place) }
        }

        return places
    }

    fun getPlacesByName(value: String): List<Place> {
        val places = mutableListOf<Place>()
        val selection = "${PlaceEntry.COLUMN_NAME} LIKE ?"
        val selectionArgs = arrayOf("%$value%")

        database.query(
            PlaceEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        ).use { cursor ->
            with(cursor) {
                while (moveToNext()) {
                    try {
                        val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                        val name = getString(getColumnIndexOrThrow(PlaceEntry.COLUMN_NAME))
                        val address = getString(getColumnIndexOrThrow(PlaceEntry.COLUMN_ADDRESS))
                        val category = getString(getColumnIndexOrThrow(PlaceEntry.COLUMN_CATEGORY))
                        val createdAt = getLong(getColumnIndexOrThrow(PlaceEntry.COLUMN_CREATED_AT))

                        places.add(Place(id, name, address, category, createdAt))
                    } catch (e: IllegalArgumentException) {
                        continue
                    }
                }
            }
        }

        return places
    }
}