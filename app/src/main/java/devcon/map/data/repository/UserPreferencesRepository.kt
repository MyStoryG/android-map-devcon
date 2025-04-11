package devcon.map.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import devcon.map.UserPreferences
import devcon.map.model.Location
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

class UserPreferencesRepository(
    private val userPreferencesDataStore: DataStore<UserPreferences>,
) {
    val userPreferencesFlow = userPreferencesDataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> {
                    Log.e(TAG, "Error reading user preferences.", exception)
                    emit(UserPreferences.getDefaultInstance())
                }

                else -> throw exception
            }
        }

    suspend fun updateLastKnownLocation(location: Location) {
        userPreferencesDataStore.updateData { currentPreferences ->
            currentPreferences.toBuilder()
                .setLastKnownLatitude(location.latitude)
                .setLastKnownLongitude(location.longitude)
                .build()
        }
    }

    suspend fun fetchInitialPreferences() = userPreferencesDataStore.data.first()

    companion object {
        private const val TAG = "UserPreferencesRepository"
    }
}
