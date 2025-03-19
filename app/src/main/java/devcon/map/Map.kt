package devcon.map

import android.app.Application
import devcon.map.data.DatabaseManager

class Map : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
    }
}