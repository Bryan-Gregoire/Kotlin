package g53735.mobg5.myapplication.database

import androidx.lifecycle.LiveData

class UserRepository(private val userDatabaseDao: UserDatabaseDao) {

    fun getUser(key: String): User? = userDatabaseDao.getUser(key)

    fun addUser(user: User): Unit {
        if(userDatabaseDao.getUser(user.email) == null ) {
            userDatabaseDao.insert(user)
        } else {
            userDatabaseDao.update(user)
        }
    }

    fun getAllUsers(): LiveData<List<User>> = userDatabaseDao.getAllUsers()
}