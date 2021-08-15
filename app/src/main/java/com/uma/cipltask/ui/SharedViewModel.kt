package com.uma.cipltask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uma.cipltask.data.model.GitRepoResponseItem

class SharedViewModel: ViewModel() {
    val item = MutableLiveData<GitRepoResponseItem>()
    val postData = MutableLiveData<String>()

    fun sendItem(data: GitRepoResponseItem) {
        item.value = data
    }

    fun sendPostData(data: String) {
        postData.value = data
    }
}