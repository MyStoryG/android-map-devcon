package devcon.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import devcon.data.PlaceRepository
import devcon.learn.contacts.R

class MainActivity : AppCompatActivity() {
    private val placeRepository: PlaceRepository by lazy {
        (application as MapApplication).placeRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapList = placeRepository.getPlaceList()
        println("mapList.size = ${mapList.size}")
    }
}
