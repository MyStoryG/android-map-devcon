package devcon.map.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import devcon.learn.contacts.R
import devcon.map.data.DatabaseManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // db test
        DatabaseManager.insert("test", "test address")
    }
}
