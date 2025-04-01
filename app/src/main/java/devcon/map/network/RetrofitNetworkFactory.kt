package devcon.map.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNetworkFactory {
    private const val KAKAO_MAP_API_BASE_URL = "https://dapi.kakao.com"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(KAKAO_MAP_API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideKakaoMapApi(): KakaoMapApi = retrofit.create(KakaoMapApi::class.java)
}
