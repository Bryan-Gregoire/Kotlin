package g53735.mobg5.cryptop.connection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import g53735.mobg5.cryptop.database.User
import g53735.mobg5.cryptop.database.UserDatabase
import g53735.mobg5.cryptop.database.UserRepository

class ConnectionViewModel(application: Application) : AndroidViewModel(application) {

    private val _eventConnection = MutableLiveData<Boolean>()
    val eventConnection: LiveData<Boolean>
        get() = _eventConnection

    private var userRepository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDatabaseDao
        userRepository = UserRepository(userDao)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return userRepository.getAllUsers()
    }


    fun onConnection() {
        _eventConnection.value = true
    }

    fun onConnectionComplete() {
        _eventConnection.value = false
    }
}