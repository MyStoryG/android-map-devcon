package devcon.map.restapi

import devcon.learn.contacts.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KeywordSearchService {
    @Headers("Authorization: KakaoAK ${BuildConfig.API_KEY}")
    @GET("v2/local/search/keyword.json")
    fun searchKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 15,
        @Query("sort") sort: String? = null
    ): Call<KeywordSearchResponse>
}