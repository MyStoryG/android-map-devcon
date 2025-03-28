package devcon.data

import android.content.SharedPreferences
import androidx.core.content.edit

class SearchWordRepository(
    private val searchWordPreference: SharedPreferences,
) {
    private var items = mutableListOf<String>()

    init {
        val savedSearchWord =
            searchWordPreference.getString("SEARCH_WORD", "")

        if (!savedSearchWord.isNullOrBlank()) {
            savedSearchWord
                .split(",")
                ?.let { items.addAll(it) }
        }
    }

    fun add(item: String) {
        if (items.contains(item)) {
            items.remove(item)
        }

        items.add(0, item)
        saveData()
    }

    fun remove(item: String): Boolean {
        val removed = items.remove(item)
        if (removed) {
            saveData()
        }

        return removed
    }

    fun getItems(): List<String> = items.toList()

    private fun saveData() {
        searchWordPreference
            .edit {
                putString("SEARCH_WORD", items.joinToString(","))
            }
    }
}
