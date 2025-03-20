package devcon.map.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.MainViewModelFactory
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
        viewModel.onInit()
    }

    private fun setViewLogics() {
        binding.contentRecyclerView.run {
            adapter = ContentRecyclerAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.historyRecyclerView.run {
            adapter = HistoryRecyclerAdapter()
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}