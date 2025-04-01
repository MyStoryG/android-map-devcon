package devcon.map

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import devcon.map.data.repository.SearchRepository
import devcon.map.data.source.local.SearchLocalDataSource
import devcon.map.data.source.remote.SearchRemoteDataSource
import devcon.map.database.DatabaseHelper
import devcon.map.network.RetrofitNetworkFactory

class MapApplication : Application() {
    lateinit var searchRepository: SearchRepository

    override fun onCreate() {
        super.onCreate()

        setupKakaoSdk()
        val databaseHelper = DatabaseHelper(this)

        searchRepository = SearchRepository(
            SearchLocalDataSource(databaseHelper.provideKeywordDao()),
            SearchRemoteDataSource(RetrofitNetworkFactory.provideKakaoMapApi()),
        )
    }

    private fun setupKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
