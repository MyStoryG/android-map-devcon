package devcon.map

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import devcon.map.databinding.ActivityMainBinding
import devcon.map.feature.SearchActivity
import devcon.map.model.Place

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var kakaoMap: KakaoMap

    private val searchActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE, Place::class.java)
            } else {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE)
            }?.let { place ->
                moveKakaoMapCamera(place)
                showPlaceBottomSheet(place)
            }
        }
    }

    private fun moveKakaoMapCamera(place: Place) {
        val position = LatLng.from(place.latitude, place.longitude)
        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(position, MOVE_ZOOM_LEVEL))
    }

    private fun showPlaceBottomSheet(place: Place) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.textviewPlaceName.text = place.name
        binding.textviewPlaceAddress.text = place.address
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        initializeBottomSheet()
        initializeEditText()
        initializeMapView()
    }

    private fun initializeBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initializeEditText() {
        binding.edittextSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            searchActivityLauncher.launch(intent)
        }
    }

    private fun initializeMapView() {
        binding.mapview.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {} // NOP

                override fun onMapError(e: Exception) {
                    // TODO: Error handling
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    this@MainActivity.kakaoMap = kakaoMap
                    kakaoMap.setOnCameraMoveEndListener { kakaoMap, cameraPosition, gestureType ->
                        // TODO: Save last known position
                        Log.i("MainActivity", "Gesture Type: $gestureType")
                        Log.i("MainActivity", "Camera Position: $cameraPosition")
                    }
                }

                override fun getPosition(): LatLng {
                    // TODO: Set last known position
                    return super.getPosition()
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        binding.mapview.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapview.resume()
    }

    companion object {
        private const val MOVE_ZOOM_LEVEL = 15

        const val RESULT_SEARCH_PLACE = "result_search_place"
    }
}
