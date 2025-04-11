package devcon.map.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import devcon.map.UserPreferences

object PreferencesDataStoreFactory {
    private const val DATA_STORE_FILE_NAME = "user_preferences.pb"
    private const val PREFERENCES_FILE_NAME = "user_preferences"

    private const val DEFAULT_LATITUDE = 37.402005
    private const val DEFAULT_LONGITUDE = 127.108621

    private val Context.dataStore: DataStore<UserPreferences> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = UserPreferencesSerializer,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context = context,
                    sharedPreferencesName = PREFERENCES_FILE_NAME
                ) { _, currentData ->
                    if (currentData.lastKnownLatitude == 0.0 && currentData.lastKnownLongitude == 0.0) {
                        currentData.toBuilder()
                            .setLastKnownLatitude(DEFAULT_LATITUDE)
                            .setLastKnownLongitude(DEFAULT_LONGITUDE)
                            .build()
                    } else {
                        currentData
                    }
                }
            )
        },
    )

    fun provideUserPreferences(
        applicationContext: Context,
    ): DataStore<UserPreferences> = applicationContext.dataStore
}
