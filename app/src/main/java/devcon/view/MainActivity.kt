package devcon.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devcon.MapApplication
import devcon.learn.contacts.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var searchEditText: EditText
    private lateinit var searchWordRecyclerView: RecyclerView
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var emptyResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = (application as MapApplication)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application.placeRepository, application.searchWordRepository),
            )[MainViewModel::class.java]

        initEditText()
        initSearchWordView()
        initPlaceView()
    }

    private fun initSearchWordView() {
        searchWordRecyclerView = findViewById(R.id.search_word_recycler_view)
        searchWordRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val searchWordAdapter =
            SearchWordAdapter(
                onItemClick = {
                    changeEditText(it)
                    viewModel.onClickPlace(it)
                },
                onItemDelete = {
                    viewModel.onClickDeleteSearchWord(it)
                },
            )

        searchWordRecyclerView.adapter = searchWordAdapter

        viewModel.searchWordsObservableData.observe { searchWords ->
            if (searchWords.isEmpty()) {
                searchWordRecyclerView.visibility = View.GONE
            } else {
                searchWordRecyclerView.visibility = View.VISIBLE
            }

            searchWordAdapter.submitList(searchWords)
        }
    }

    private fun initPlaceView() {
        val placeAdapter =
            PlaceAdapter(
                onItemClick = {
                    changeEditText(it.name)
                    viewModel.onClickPlace(it.name)
                },
            )

        placeRecyclerView = findViewById(R.id.place_recycler_view)
        emptyResultTextView = findViewById(R.id.main_empty_result_text_view)
        placeRecyclerView.layoutManager = LinearLayoutManager(this)
        placeRecyclerView.adapter = placeAdapter

        viewModel.placeObservableData.observe { places ->
            if (places.isEmpty()) {
                emptyResultTextView.visibility = View.VISIBLE
                placeRecyclerView.visibility = View.GONE
            } else {
                emptyResultTextView.visibility = View.GONE
                placeRecyclerView.visibility = View.VISIBLE
            }

            placeAdapter.submitList(places)
        }
    }

    private fun changeEditText(placeName: String) {
        searchEditText.setText(placeName)
        searchEditText.setSelection(placeName.length)
    }

    private fun initEditText() {
        searchEditText = findViewById(R.id.main_search_edit_text)
        searchEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val searchText = s.toString()
                    viewModel.loadPlaces(searchText)
                }
            },
        )
    }
}
