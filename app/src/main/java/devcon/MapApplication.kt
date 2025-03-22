package devcon

import android.app.Application
import devcon.data.PlaceDatabaseManager
import devcon.data.PlaceRepository
import devcon.data.SearchWordPreference
import devcon.data.SearchWordRepository

class MapApplication : Application() {
    val placeRepository: PlaceRepository by lazy {
        val dbHelper = PlaceDatabaseManager.init(this)
        PlaceRepository(dbHelper)
    }

    val searchWordRepository: SearchWordRepository by lazy {
        val preference = SearchWordPreference.init(this)
        SearchWordRepository(preference)
    }
}
