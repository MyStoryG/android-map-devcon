package devcon.map

import android.app.Application
import devcon.map.data.KeywordRepository
import devcon.map.database.DatabaseHelper
import devcon.map.database.KeywordDao

class MapApplication : Application() {
    lateinit var keywordRepository: KeywordRepository

    override fun onCreate() {
        super.onCreate()

        val databaseHelper = DatabaseHelper(this)
        val keywordDao = KeywordDao(databaseHelper.writableDatabase)

        keywordRepository = KeywordRepository(keywordDao)
    }
}
