package devcon.data

import android.content.Context

object PlaceDatabaseManager {
    private lateinit var instance: PlaceDatabaseHelper

    fun init(context: Context): PlaceDatabaseHelper {
        instance = PlaceDatabaseHelper(context.applicationContext)
        return instance
    }
}
