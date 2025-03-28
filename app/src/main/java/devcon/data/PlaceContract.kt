package devcon.data

import android.provider.BaseColumns

object PlaceContract {
    object PlaceEntry : BaseColumns {
        const val TABLE_NAME = "places"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }
}
