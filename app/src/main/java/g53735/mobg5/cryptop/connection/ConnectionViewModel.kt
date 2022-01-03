package g53735.mobg5.cryptop.connection

import android.app.Application
import androidx.lifecycle.*
import g53735.mobg5.cryptop.database.User
import g53735.mobg5.cryptop.database.UserDatabase
import g53735.mobg5.cryptop.database.UserDatabaseDao
import kotlinx.coroutines.launch

class ConnectionViewModel(dataUserDao: UserDatabaseDao) : ViewModel() {

    val userDao = dataUserDao

    private val _eventConnection = MutableLiveData<Boolean>()
    val eventConnection: LiveData<Boolean>
        get() = _eventConnection


    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    fun addUser(userEmail: String) {
        viewModelScope.launch {
            if(userDao.getUser(userEmail) == null) {
                val user = User(userEmail)
                userDao.insert(user)
            }
        }
    }

    fun onConnection() {
        _eventConnection.value = true
    }

    fun onConnectionComplete() {
        _eventConnection.value = false
    }


}