package g53735.mobg5.cryptop.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_login WHERE email = :key")
    fun getUser(key: String): User?

    @Query("SELECT * FROM user_login")
    fun getAllUsers(): LiveData<List<User>>
}