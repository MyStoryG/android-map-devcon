package devcon.data

import android.content.Context
import android.content.SharedPreferences

object SearchWordPreference {
    private lateinit var instance: SharedPreferences

    fun init(context: Context): SharedPreferences {
        instance =
            context.applicationContext.getSharedPreferences(
                "SearchWordPref",
                Context.MODE_PRIVATE,
            )
        return instance
    }
}
