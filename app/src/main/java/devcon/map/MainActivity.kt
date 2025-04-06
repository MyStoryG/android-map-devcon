package devcon.map

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import devcon.map.databinding.ActivityMainBinding
import devcon.map.feature.SearchActivity
import devcon.map.model.Place

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val searchActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE, Place::class.java)
            } else {
                result.data?.getParcelableExtra(RESULT_SEARCH_PLACE)
            }?.let { place ->
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        initializeEditText()
        initializeMapView()
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
                override fun onMapReady(kakaoMap: KakaoMap) {} // NOP
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
        const val RESULT_SEARCH_PLACE = "result_search_place"
    }
}
