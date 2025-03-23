package devcon.map.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.data.repository.CafeRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cafeRepository = CafeRepository()
        val factory = MainViewModelFactory(repository = cafeRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.runSearchTitle(p0.toString())
            }
        })

        mainViewModel.searchResult.observe(this) { cafes ->
            Log.d("hoon92", "cafes = $cafes")
        }
    }
}
