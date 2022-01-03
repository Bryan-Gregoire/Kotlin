package g53735.mobg5.cryptop.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.cryptop.database.UserDatabaseDao

class ConnectionViewModelFactory(private val dataUserDao: UserDatabaseDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectionViewModel::class.java)) {
            return ConnectionViewModel(dataUserDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
