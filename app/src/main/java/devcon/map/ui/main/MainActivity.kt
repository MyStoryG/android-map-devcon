package devcon.map.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.data.repository.CafeRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainSearchResultAdapter: MainSearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
        startObserve()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                mainSearchResultAdapter = MainSearchResultAdapter { cafeTitle ->
                    mainViewModel.addSavedSearch(cafeTitle)
                }
                adapter = mainSearchResultAdapter
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.runSearchTitle(p0.toString())
            }
        })
    }

    private fun initViewModel() {
        val cafeRepository = CafeRepository()
        val factory = MainViewModelFactory(repository = cafeRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun startObserve() {
        mainViewModel.searchResult.observe(this) { cafes ->
            Log.d("hoon92", "cafes = $cafes")
            if (cafes.isEmpty()) {
                binding.tvEmptyResult.visibility = View.VISIBLE
                binding.rvSearchResult.visibility = View.GONE
            } else {
                binding.tvEmptyResult.visibility = View.GONE
                binding.rvSearchResult.visibility = View.VISIBLE
            }
            mainSearchResultAdapter.submitList(cafes)
        }

        mainViewModel.savedSearch.observe(this) {
            Log.d("hoon92", "item List = ${it.toString()}")
        }
    }
}
