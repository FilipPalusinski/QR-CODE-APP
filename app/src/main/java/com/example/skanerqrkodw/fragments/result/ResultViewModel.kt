package com.example.skanerqrkodw.fragments.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skanerqrkodw.fragments.history.database.History
import com.example.skanerqrkodw.fragments.history.database.HistoryDao
import kotlinx.coroutines.launch

class ResultViewModel(dataSource: HistoryDao): ViewModel() {

    lateinit var scannedQrCode: String

    var contentText = MutableLiveData<String>("")

    var makeVisible = MutableLiveData<Boolean>(false)

    val database = dataSource

    fun checkString(){

        if (scannedQrCode.contentEquals("")){
            contentText.value = "skanowanie nie powiodło się"
            makeVisible.value = false
        }
        else if(scannedQrCode.startsWith("http://") || scannedQrCode.startsWith("https://") || scannedQrCode.startsWith("www.") || scannedQrCode.endsWith(".pl") ) {
            makeVisible.value = true

            contentText.value = scannedQrCode

            viewModelScope.launch {
                val history = History()
                history.scannedString = scannedQrCode
                database.insert(history)
            }

        } else {
            makeVisible.value = false
            viewModelScope.launch {
                val history = History()
                history.scannedString = scannedQrCode
                database.insert(history)
            }
            //binding.content.text = scannedQrCode
            contentText.value = scannedQrCode

        }
    }








}