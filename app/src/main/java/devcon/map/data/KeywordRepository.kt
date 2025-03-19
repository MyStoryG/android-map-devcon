package devcon.map.data

import android.content.Context
import devcon.map.database.KeywordDao
import devcon.map.database.KeywordDatabaseHelper
import devcon.map.model.Keyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KeywordRepository(
    context: Context,
) {
    private val keywordDao: KeywordDao

    init {
        val databaseHelper = KeywordDatabaseHelper(context)
        val database = databaseHelper.writableDatabase

        keywordDao = KeywordDao(database)
    }

    fun upsert(keyword: Keyword): Flow<Unit> = flow { emit(keywordDao.upsert(keyword)) }

    fun getKeywords(): Flow<List<Keyword>> = flow { emit(keywordDao.getKeywords()) }

    fun delete(keyword: Keyword): Flow<Unit> = flow { emit(keywordDao.delete(keyword)) }
}
