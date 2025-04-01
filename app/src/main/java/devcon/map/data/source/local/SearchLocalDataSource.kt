package devcon.map.data.source.local

import devcon.map.database.dao.KeywordDao
import devcon.map.model.Keyword
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchLocalDataSource(
    private val keywordDao: KeywordDao,
    private val externalDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun searchKeyword(keyword: Keyword): List<Keyword> =
        withContext(externalDispatcher) { keywordDao.upsert(keyword) }

    suspend fun fetchKeywords(): List<Keyword> =
        withContext(externalDispatcher) { keywordDao.getKeywords() }

    suspend fun deleteKeyword(keyword: Keyword): List<Keyword> =
        withContext(externalDispatcher) { keywordDao.delete(keyword) }
}
