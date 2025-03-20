package devcon.map.repository

import android.content.Context
import devcon.map.data.MyDatabaseHelper
import devcon.map.data.SampleData

class SampleRepository(context: Context) {
    private val dbHelper = MyDatabaseHelper(context)

    fun initData() {
        if (readData().isEmpty()) {
            for (i in 1..200) {
                insertData(SampleData(i.toLong(), "title $i"))
            }
        }
    }

    fun insertData(data: SampleData): Long {
        return dbHelper.insertData(data)
    }

    fun readData(): List<SampleData> {
        return dbHelper.readData()
    }

    fun deleteData(id: Long): Int {
        return dbHelper.deleteData(id)
    }

    fun updateData(data: SampleData): Int {
        return dbHelper.updateData(data)
    }

}