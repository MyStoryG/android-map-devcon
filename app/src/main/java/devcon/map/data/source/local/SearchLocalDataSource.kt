package devcon.map.data.source.local

import devcon.map.database.dao.KeywordDao
import devcon.map.model.Keyword
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchLocalDataSource(
    private val keywordDao: KeywordDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun searchKeyword(keyword: Keyword): List<Keyword> =
        withContext(ioDispatcher) { keywordDao.upsert(keyword) }

    suspend fun fetchKeywords(): List<Keyword> =
        withContext(ioDispatcher) { keywordDao.getKeywords() }

    suspend fun deleteKeyword(keyword: Keyword): List<Keyword> =
        withContext(ioDispatcher) { keywordDao.delete(keyword) }
}
