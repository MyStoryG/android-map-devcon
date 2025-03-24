package devcon.map.data

import devcon.map.database.PlaceDao
import devcon.map.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRepository(
    private val placeDao: PlaceDao,
) {
    fun insertAll(places: List<Place>): Flow<List<Place>> = flow { emit(placeDao.insertAll(places)) }

    fun getPlacesByName(value: String): Flow<List<Place>> = flow { emit(placeDao.getPlacesByName(value)) }
}
