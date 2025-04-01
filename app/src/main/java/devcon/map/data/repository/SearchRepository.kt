package devcon.map.data.repository

import devcon.map.data.source.local.SearchLocalDataSource
import devcon.map.model.KakaoMapSearchResponse
import devcon.map.model.Keyword
import devcon.map.data.source.remote.SearchRemoteDataSource
import retrofit2.Response

class SearchRepository(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
) {
    suspend fun getSearchKeyword(
        page: Int,
        size: Int,
        query: String,
    ): Response<KakaoMapSearchResponse> = searchRemoteDataSource.getSearchKeyword(page, size, query)

    suspend fun searchKeyword(keyword: Keyword): List<Keyword> =
        searchLocalDataSource.searchKeyword(keyword)

    suspend fun fetchSearchKeyword(): List<Keyword> = searchLocalDataSource.fetchKeywords()

    suspend fun deleteKeyword(keyword: Keyword): List<Keyword> =
        searchLocalDataSource.deleteKeyword(keyword)
}
