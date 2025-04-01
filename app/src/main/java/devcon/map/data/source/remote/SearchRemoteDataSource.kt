package devcon.map.data.source.remote

import devcon.map.model.KakaoMapSearchResponse
import devcon.map.network.KakaoMapApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchRemoteDataSource(
    private val kakaoMapApi: KakaoMapApi,
    private val externalDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getSearchKeyword(
        page: Int = 1,
        size: Int = 15,
        query: String,
    ): Response<KakaoMapSearchResponse> = withContext(externalDispatcher) {
        kakaoMapApi.getSearchKeyword(page, size, query)
    }
}
