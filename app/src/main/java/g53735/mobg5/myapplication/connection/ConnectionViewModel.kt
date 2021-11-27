package g53735.mobg5.myapplication.connection

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConnectionViewModel : ViewModel() {

    private val _eventConnection = MutableLiveData<Boolean>()
    val eventConnection: LiveData<Boolean>
        get() = _eventConnection

    fun onConnection() {
        _eventConnection.value = true
    }

    fun onConnectionComplete() {
        _eventConnection.value = false
    }
}