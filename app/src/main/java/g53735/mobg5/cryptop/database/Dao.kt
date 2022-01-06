package g53735.mobg5.cryptop.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insert(entities: List<T>)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun update(entities: List<T>)
}

@Dao
abstract class UserDatabaseDao : BaseDao<User> {

    @Query("SELECT * FROM user_login WHERE email = :key")
    abstract suspend fun getUser(key: String): User?

    @Query("SELECT * FROM user_login")
    abstract fun getAllUsers(): LiveData<List<User>>
}

@Dao
abstract class CryptoDatabaseDao : BaseDao<Crypto> {

    @Query("SELECT * FROM crypto_table WHERE cryptoId = :key")
    abstract fun getCrypto(key: Long): LiveData<Crypto>

    @Query("SELECT * FROM crypto_table WHERE cryptoId = :key")
    abstract suspend fun getFavoriteCrypto(key: Long): Crypto

    @Query("SELECT * FROM crypto_table")
    abstract fun getAllCryptos(): LiveData<List<Crypto>>

    @Query("SELECT * FROM crypto_table WHERE favorite = 1")
    abstract suspend fun getAllFavorites(): List<Crypto>

    @Query("SELECT * FROM crypto_table order by rank LIMIT 100")
    abstract fun getTop100Cryptos(): LiveData<List<Crypto>>

    @Query("SELECT * FROM crypto_table order by rank LIMIT :limit")
    abstract suspend fun getTopCryptos(limit: Int): List<Crypto>

    @Query("DELETE FROM crypto_table")
    abstract suspend fun clear()

}