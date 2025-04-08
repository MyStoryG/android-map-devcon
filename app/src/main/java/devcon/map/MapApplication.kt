package devcon.map

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk
import devcon.map.data.repository.UserPreferencesRepository
import devcon.map.data.repository.SearchRepository
import devcon.map.data.source.local.SearchLocalDataSource
import devcon.map.data.source.remote.SearchRemoteDataSource
import devcon.map.database.DatabaseHelper
import devcon.map.datastore.PreferencesDataStoreFactory
import devcon.map.network.RetrofitNetworkFactory

class MapApplication : Application() {
    val searchRepository: SearchRepository by lazy {
        SearchRepository(
            SearchLocalDataSource(DatabaseHelper(this).provideKeywordDao()),
            SearchRemoteDataSource(RetrofitNetworkFactory.provideKakaoMapApi()),
        )
    }
    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(
            PreferencesDataStoreFactory.provideUserPreferences(this),
        )
    }

    override fun onCreate() {
        super.onCreate()

        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
