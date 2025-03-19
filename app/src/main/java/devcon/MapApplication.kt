package devcon

import android.app.Application
import devcon.data.PlaceDatabaseManager
import devcon.data.PlaceRepository

class MapApplication : Application() {
    val placeRepository: PlaceRepository by lazy {
        val dbHelper = PlaceDatabaseManager.init(this)
        PlaceRepository(dbHelper)
    }
}
