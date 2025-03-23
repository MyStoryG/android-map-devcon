package devcon.map.data.repository

import devcon.map.data.local.DatabaseManager
import devcon.map.data.model.CafeDTO

class CafeRepository {
    fun getCafesByTitle(title: String): List<CafeDTO> {
        return DatabaseManager.getCafesByTitle(title)
    }
}