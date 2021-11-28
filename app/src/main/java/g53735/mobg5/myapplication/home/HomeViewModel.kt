package g53735.mobg5.myapplication.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import g53735.mobg5.myapplication.database.User
import g53735.mobg5.myapplication.database.UserDatabase
import g53735.mobg5.myapplication.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(loginEmail: String, application: Application) : AndroidViewModel(application) {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private lateinit var userRepository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDatabaseDao
        userRepository = UserRepository(userDao)
        _email.value = loginEmail
        addUser(User("${_email.value}"))

        userRepository.getAllUsers().observeForever { users ->
            Log.i("HomeViewModel", "Je get all ")
            users.forEach {
                Log.i("HomeViewModel", it.email)
            }
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
            Log.i("HomeViewModel", "J'ai add ${user.email}")
        }
    }

}