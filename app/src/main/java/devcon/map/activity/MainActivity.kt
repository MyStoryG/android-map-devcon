package devcon.map.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.MainViewModelFactory
import devcon.map.abstracts.KakaoMapActivity
import devcon.map.adapters.ContentRecyclerAdapter
import devcon.map.adapters.HistoryRecyclerAdapter
import devcon.map.repository.HistoryRepository
import devcon.map.repository.RetrofitRepository
import devcon.map.viewmodel.MainViewModel

class MainActivity : KakaoMapActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(HistoryRepository(applicationContext), RetrofitRepository())
        ).get(MainViewModel::class.java)
    }

    override val kakaoMapView by lazy { binding.mapView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViewLogics()
    }

    private fun setViewLogics() {

        fun enableResultArea(enabled: Boolean) {
            binding.searchResultArea.visibility = if (enabled) View.VISIBLE else View.GONE
        }
        binding.run {
            mapView.start(mapLifeCycleCallback, kakaoMapReadyCallback)

            contentRecyclerView.run {
                adapter = ContentRecyclerAdapter(
                    itemFlow = viewModel.contentFlow,
                    onClick = { viewModel.onSelectItem(it) }
                )
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(
                    DividerItemDecoration(
                        this@MainActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }

            historyRecyclerView.run {
                adapter = HistoryRecyclerAdapter(
                    itemFlow = viewModel.historyFlow,
                    onClick = { viewModel.onDeleteHistory(it) }
                )
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    DividerItemDecoration(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL
                    )
                )

            }

            searchEditText.run {
                setOnFocusChangeListener { _, hasFocus ->
                    enableResultArea(hasFocus)
                }
                setOnClickListener {
                    enableResultArea(true)
                }

                addTextChangedListener(
                    object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                        override fun afterTextChanged(p0: Editable?) {
                            viewModel.afterChanged(p0.toString())
                        }

                    }
                )
            }
        }
        onBackPressedDispatcher.addCallback {
            if (binding.searchResultArea.visibility == View.VISIBLE) {
                enableResultArea(false)
            } else {
                finish()
            }
        }
    }
}