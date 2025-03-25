package devcon.map.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.MainViewModelFactory
import devcon.map.SearchTextWatcher
import devcon.map.adapters.ContentRecyclerAdapter
import devcon.map.adapters.HistoryRecyclerAdapter
import devcon.map.repository.SampleRepository
import devcon.map.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(SampleRepository(applicationContext))
        ).get(MainViewModel::class.java)
        setContentView(binding.root)

        setViewLogics()
        viewModel.checkDB()
    }

    private fun setViewLogics() {
        binding.run {
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

            searchEditText.addTextChangedListener(SearchTextWatcher { viewModel.afterChanged(it) })
            clearButton.setOnClickListener {
                searchEditText.text?.clear()
            }
        }
    }
}