package devcon.map

import android.app.Application
import devcon.map.data.local.DatabaseManager

class Map : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
        insertDummyData()
    }

    private fun insertDummyData() {
        for (i in 1 .. 500) {
            DatabaseManager.insert("cafe${i}", "서울 성동구 성수동 ${i}")
        }
    }
}