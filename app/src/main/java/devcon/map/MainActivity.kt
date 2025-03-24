package devcon.map

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import devcon.learn.contacts.R
import devcon.learn.contacts.databinding.ActivityMainBinding
import devcon.map.model.Keyword
import devcon.map.ui.HorizontalSpaceDecoration
import devcon.map.ui.KeywordAdapter
import devcon.map.ui.KeywordViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val keywordViewModel by viewModels<KeywordViewModel> { KeywordViewModel.Factory }

    private lateinit var binding: ActivityMainBinding
    private lateinit var keywordAdapter: KeywordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        updateUI()
    }

    private fun setupUI() {
        initializeRecyclerView()
        initializeEditText()
    }

    private fun updateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                keywordViewModel.uiState.collect { uiState ->
                    keywordAdapter.submitList(uiState.keywords)
                    binding.recyclerviewKeyword.visibility =
                        if (uiState.isEmpty) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        keywordAdapter = KeywordAdapter { keyword -> keywordViewModel.delete(keyword) }

        binding.recyclerviewKeyword.apply {
            adapter = keywordAdapter

            val margin = resources.getDimension(R.dimen.margin_medium)
            val padding = resources.getDimension(R.dimen.padding_extra_small)
            addItemDecoration(HorizontalSpaceDecoration(padding, padding, margin, margin))
        }
    }

    private fun initializeEditText() {
        binding.edittextSearch.setOnKeyListener { _, keyCode, keyEvent ->
            val isKeyCodeEnter = keyCode == KeyEvent.KEYCODE_ENTER
            val isActionDown = keyEvent.action == KeyEvent.ACTION_DOWN

            if (isKeyCodeEnter && isActionDown) {
                val keyword = binding.edittextSearch.text?.toString()
                if (!keyword.isNullOrBlank()) {
                    keywordViewModel.upsert(Keyword(word = keyword))
                }

                binding.edittextSearch.text?.clear()
            }

            isKeyCodeEnter && isActionDown
        }
    }
}
