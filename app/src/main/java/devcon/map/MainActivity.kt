package devcon.map

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder
import com.kakao.vectormap.label.LabelTextStyle
import devcon.map.databinding.ActivityMainBinding
import devcon.map.feature.SearchActivity
import devcon.map.model.Location
import devcon.map.model.Place
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val userPreferencesViewModel by viewModels<UserPreferencesViewModel> { UserPreferencesViewModel.Factory }
    private val kakaoMapCompletableDeferred = CompletableDeferred<KakaoMap>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val searchActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE, Place::class.java)
            } else {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE)
            }?.let { place ->
                lifecycleScope.launch {
                    val position = place.location.run { LatLng.from(latitude, longitude) }
                    moveKakaoMapCamera(position)
                    showPlaceMarker(position, place.name)
                    showPlaceBottomSheet(place.name, place.address)
                }
            }
        }
    }

    private suspend fun moveKakaoMapCamera(position: LatLng) {
        val kakaoMap = kakaoMapCompletableDeferred.await()
        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(position, MOVE_ZOOM_LEVEL))
    }

    private suspend fun showPlaceMarker(position: LatLng, name: String) {
        val kakaoMap = kakaoMapCompletableDeferred.await()
        kakaoMap.labelManager?.let { labelManager ->
            val styles = run {
                val iconStyle = LabelStyle.from(R.drawable.icon_marker)
                val textStyle = LabelTextStyle.from(24, Color.WHITE, 4, Color.BLACK)

                labelManager.addLabelStyles(LabelStyles.from(iconStyle.setTextStyles(textStyle)))
            }
            val option = LabelOptions.from(position)
                .setStyles(styles)
                .setTexts(LabelTextBuilder().setTexts(name))

            labelManager.clearAll()
            labelManager.layer?.addLabel(option)
        }
    }

    private fun showPlaceBottomSheet(name: String, address: String) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.textviewPlaceName.text = name
        binding.textviewPlaceAddress.text = address
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupEvent()
    }

    private fun setupUI() {
        initializeBottomSheet()
        initializeEditText()
        initializeMapView()
    }

    private fun setupEvent() {
        userPreferencesViewModel.initialSetupEvent.observe(this) { userPreferences ->
            lifecycleScope.launch {
                val position = LatLng.from(
                    userPreferences.lastKnownLatitude,
                    userPreferences.lastKnownLongitude,
                )
                moveKakaoMapCamera(position)
            }
        }
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
                    kakaoMapCompletableDeferred.complete(kakaoMap)
                    kakaoMap.setOnCameraMoveEndListener { _, cameraPosition, _ ->
                        val lastKnownLocation =
                            cameraPosition.position.run { Location(latitude, longitude) }
                        userPreferencesViewModel.updateLastKnownLocation(lastKnownLocation)
                    }
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
