package devcon.map.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.database.sqlite.transaction
import devcon.map.database.KeywordContract.KeywordEntry
import devcon.map.model.Keyword

class KeywordDao(
    private val database: SQLiteDatabase,
) {
    private fun insert(keyword: Keyword): Long {
        val values = ContentValues().apply {
            put(KeywordEntry.COLUMN_WORD, keyword.word)
            put(KeywordEntry.COLUMN_SEARCHED_AT, keyword.searchedAt)
        }

        return database.insert(KeywordEntry.TABLE_NAME, null, values)
    }

    fun upsert(keyword: Keyword): List<Keyword> {
        val values = ContentValues().apply {
            put(KeywordEntry.COLUMN_WORD, keyword.word)
            put(KeywordEntry.COLUMN_SEARCHED_AT, keyword.searchedAt)
        }

        val whereClause = "${KeywordEntry.COLUMN_WORD} = ?"
        val whereArgs = arrayOf(keyword.word)

        val keywords = database.transaction {
            val updatedRows = database
                .update(KeywordEntry.TABLE_NAME, values, whereClause, whereArgs)

            if (updatedRows == 0) {
                insert(keyword)
            }

            getKeywords()
        }

        return keywords
    }

    fun getKeywords(): List<Keyword> {
        val keywords = mutableListOf<Keyword>()
        val orderBy = "${KeywordEntry.COLUMN_SEARCHED_AT} ASC"

        database.query(
            KeywordEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            orderBy,
        ).use { cursor ->
            with(cursor) {
                while (moveToNext()) {
                    try {
                        val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                        val word = getString(getColumnIndexOrThrow(KeywordEntry.COLUMN_WORD))
                        val searchedAt =
                            getLong(getColumnIndexOrThrow(KeywordEntry.COLUMN_SEARCHED_AT))

                        keywords.add(Keyword(id, word, searchedAt))
                    } catch (e: IllegalArgumentException) {
                        continue
                    }
                }
            }
        }

        return keywords
    }

    fun delete(keyword: Keyword): List<Keyword> {
        val whereClause = "${BaseColumns._ID} = ?"
        val whereArgs = arrayOf(keyword.id.toString())

        val keywords = database.transaction {
            database.delete(KeywordEntry.TABLE_NAME, whereClause, whereArgs)
            getKeywords()
        }

        return keywords
    }
}
