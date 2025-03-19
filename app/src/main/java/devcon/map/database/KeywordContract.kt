package devcon.map.database

import android.provider.BaseColumns

object KeywordContract {
    object KeywordEntry : BaseColumns {
        const val TABLE_NAME = "keywords"
        const val COLUMN_WORD = "word"
        const val COLUMN_SEARCHED_AT = "searched_at"
    }
}
