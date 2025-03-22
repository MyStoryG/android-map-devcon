package devcon.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devcon.MapApplication
import devcon.learn.contacts.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory((application as MapApplication).placeRepository),
            )[MainViewModel::class.java]

        val placeAdapter =
            PlaceAdapter(
                onItemClick = {},
            )

        val recyclerView: RecyclerView = findViewById(R.id.place_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = placeAdapter
    }
}
