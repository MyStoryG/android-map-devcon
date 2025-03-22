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
import devcon.domain.Place
import devcon.learn.contacts.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var searchEditText: EditText
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var emptyResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory((application as MapApplication).placeRepository),
            )[MainViewModel::class.java]

        initEditText()
        initListView()
    }

    private fun initListView() {
        val placeAdapter =
            PlaceAdapter(
                onItemClick = {
                    changeEditText(it)
                    viewModel.onClickPlace(it)
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

    private fun changeEditText(place: Place) {
        val placeName = place.name

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
