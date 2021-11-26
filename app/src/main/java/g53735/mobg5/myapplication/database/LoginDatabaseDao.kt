package g53735.mobg5.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface LoginDatabaseDao {

    @Insert
    fun insert(login: Login)

    @Update
    fun update(login: Login)
}