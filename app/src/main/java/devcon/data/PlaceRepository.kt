package devcon.data

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import devcon.domain.Place

class PlaceRepository(
    private val dbHelper: PlaceDatabaseHelper,
) {
    fun insert(place: Place): Long {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(PlaceContract.PlaceEntry.COLUMN_NAME, place.name)
                put(PlaceContract.PlaceEntry.COLUMN_TYPE, place.type)
                put(PlaceContract.PlaceEntry.COLUMN_ADDRESS, place.address)
                put(PlaceContract.PlaceEntry.COLUMN_LATITUDE, place.latitude)
                put(PlaceContract.PlaceEntry.COLUMN_LONGITUDE, place.longitude)
            }
        val newRowId = db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
        db.close()
        return newRowId
    }

    fun insertAll(places: List<Place>): Boolean {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        return try {
            for (place in places) {
                val values =
                    ContentValues().apply {
                        put(PlaceContract.PlaceEntry.COLUMN_NAME, place.name)
                        put(PlaceContract.PlaceEntry.COLUMN_TYPE, place.type)
                        put(PlaceContract.PlaceEntry.COLUMN_ADDRESS, place.address)
                        put(PlaceContract.PlaceEntry.COLUMN_LATITUDE, place.latitude)
                        put(PlaceContract.PlaceEntry.COLUMN_LONGITUDE, place.longitude)
                    }
                db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values)
            }
            db.setTransactionSuccessful()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.endTransaction()
        }
    }

    fun getPlaceList(): List<Place> {
        val placesList = mutableListOf<Place>()
        val db = dbHelper.readableDatabase

        val columns =
            arrayOf(
                BaseColumns._ID,
                PlaceContract.PlaceEntry.COLUMN_NAME,
                PlaceContract.PlaceEntry.COLUMN_TYPE,
                PlaceContract.PlaceEntry.COLUMN_ADDRESS,
                PlaceContract.PlaceEntry.COLUMN_LATITUDE,
                PlaceContract.PlaceEntry.COLUMN_LONGITUDE,
            )

        val cursor: Cursor =
            db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME))
                val type = getString(getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_TYPE))
                val address = getString(getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_ADDRESS))
                val latitude = getDouble(getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_LATITUDE))
                val longitude = getDouble(getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_LONGITUDE))
                placesList.add(Place(id, name, type, address, latitude, longitude))
            }
        }
        cursor.close()
        db.close()
        return placesList
    }
}
