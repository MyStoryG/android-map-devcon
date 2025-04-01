package devcon.map.network

import devcon.map.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
            .build()

        return chain.proceed(request)
    }
}
