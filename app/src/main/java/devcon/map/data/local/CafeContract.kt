package devcon.map.data.local

import android.provider.BaseColumns

object CafeContract {
    object CafeEntry : BaseColumns {
        const val TABLE_NAME = "cafe_entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_ADDRESS = "subtitle"
    }
}