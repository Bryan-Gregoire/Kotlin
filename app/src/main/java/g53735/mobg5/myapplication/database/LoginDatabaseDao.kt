package g53735.mobg5.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LoginDatabaseDao {

    @Insert
    fun insert(login: Login)

    @Update
    fun update(login: Login)

    @Query("SELECT * FROM user_login ORDER BY email")
    fun getAllEmails(): LiveData<List<Login>>
}