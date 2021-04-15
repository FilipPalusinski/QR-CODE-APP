package com.example.skanerqrkodw.fragments.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.skanerqrkodw.fragments.history.database.HistoryDao

class HistoryViewModel(val database: HistoryDao, application: Application) : AndroidViewModel(application) {
     val stories = database.getAll()
}
