package g53735.mobg5.myapplication.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel(loginEmail: String) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    init {
        _email.value = loginEmail
    }

}