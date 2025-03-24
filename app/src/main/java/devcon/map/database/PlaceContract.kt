package devcon.map.database

import android.provider.BaseColumns

object PlaceContract {
    object PlaceEntry : BaseColumns {
        const val TABLE_NAME = "places"
        const val COLUMN_NAME = "name"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_CREATED_AT = "created_at"
    }
}
