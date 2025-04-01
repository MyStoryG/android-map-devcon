package devcon.map

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import devcon.map.databinding.ActivityMainBinding
import devcon.map.model.Keyword
import devcon.map.ui.HorizontalSpaceDecoration
import devcon.map.ui.KeywordAdapter
import devcon.map.ui.PlaceAdapter
import devcon.map.ui.SearchViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val searchViewModel by viewModels<SearchViewModel> { SearchViewModel.Factory }

    private lateinit var binding: ActivityMainBinding
    private lateinit var keywordAdapter: KeywordAdapter
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        updateUI()
    }

    private fun setupUI() {
        initializeKeywordRecyclerView()
        initializePlaceRecyclerView()
        initializeEditText()
        initializeMapView()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect { uiState ->
                    keywordAdapter.submitList(uiState.keywords)
                    binding.recyclerviewKeyword.visibility =
                        if (uiState.keywords.isEmpty()) View.GONE else View.VISIBLE

                    placeAdapter.submitList(uiState.places)
                    binding.textviewNoMatchResults.visibility =
                        if (uiState.places.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun initializeKeywordRecyclerView() {
        keywordAdapter = KeywordAdapter { keyword -> searchViewModel.deleteKeyword(keyword) }

        binding.recyclerviewKeyword.apply {
            adapter = keywordAdapter

            val margin = resources.getDimension(R.dimen.margin_medium)
            val padding = resources.getDimension(R.dimen.padding_extra_small)
            addItemDecoration(HorizontalSpaceDecoration(padding, padding, margin, margin))
        }
    }

    private fun initializePlaceRecyclerView() {
        placeAdapter = PlaceAdapter { place ->
            searchViewModel.searchKeyword(Keyword(word = place.name))
        }

        binding.recyclerviewPlace.apply {
            adapter = placeAdapter

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initializeEditText() {
        binding.edittextSearch.addTextChangedListener { text ->
            // TODO: Debounce
            searchViewModel.getSearchKeyword(1, 15, text.toString())
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
}
