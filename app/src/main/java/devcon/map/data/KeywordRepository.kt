package devcon.map.data

import devcon.map.database.KeywordDao
import devcon.map.model.Keyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KeywordRepository(
    private val keywordDao: KeywordDao,
) {
    fun upsert(keyword: Keyword): Flow<Unit> = flow { emit(keywordDao.upsert(keyword)) }

    fun getKeywords(): Flow<List<Keyword>> = flow { emit(keywordDao.getKeywords()) }

    fun delete(keyword: Keyword): Flow<Unit> = flow { emit(keywordDao.delete(keyword)) }
}
