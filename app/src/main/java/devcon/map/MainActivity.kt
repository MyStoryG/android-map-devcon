package devcon.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import devcon.map.databinding.ActivityMainBinding
import devcon.map.feature.SearchActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        binding.edittextSearch.setOnClickListener { SearchActivity.start(this) }
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
}
