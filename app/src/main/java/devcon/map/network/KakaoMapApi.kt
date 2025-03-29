package devcon.map.network

import devcon.map.model.KakaoMapSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoMapApi {
    @GET("v2/local/search/keyword")
    suspend fun getSearchKeyword(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("query") query: String,
    ): Response<KakaoMapSearchResponse>
}
