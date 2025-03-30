package devcon.map.repository

import devcon.map.restapi.KeywordDocument
import devcon.map.restapi.KeywordSearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /** @param query string to query
     * @param page quantity of page
     * @return result of the query **/
    suspend fun searchKeyword(
        query: String,
        page: Int
    ): List<KeywordDocument> = withContext(Dispatchers.IO) {
        val out = mutableListOf<KeywordDocument>()
        retrofit.create(KeywordSearchService::class.java).run {
            for (i in 1..page) {
                val response = searchKeyword(query, page = i)
                    .execute()
                out.addAll(response.body()?.documents ?: emptyList())   // 쿼리 결과 수집
            }
        }
        return@withContext out
    }

}