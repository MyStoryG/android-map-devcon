package devcon.map

import android.app.Application
import devcon.map.data.KeywordRepository
import devcon.map.data.PlaceRepository
import devcon.map.database.DatabaseHelper
import devcon.map.database.KeywordDao
import devcon.map.database.PlaceDao

class MapApplication : Application() {
    lateinit var keywordRepository: KeywordRepository
    lateinit var placeRepository: PlaceRepository

    override fun onCreate() {
        super.onCreate()

        val databaseHelper = DatabaseHelper(this)
        val keywordDao = KeywordDao(databaseHelper.writableDatabase)
        val placeDao = PlaceDao(databaseHelper.writableDatabase)

        keywordRepository = KeywordRepository(keywordDao)
        placeRepository = PlaceRepository(placeDao)
    }
}
