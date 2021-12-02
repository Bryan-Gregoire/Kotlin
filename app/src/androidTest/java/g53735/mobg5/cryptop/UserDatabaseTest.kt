package g53735.mobg5.cryptop

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import g53735.mobg5.cryptop.database.User
import g53735.mobg5.cryptop.database.UserDatabase
import g53735.mobg5.cryptop.database.UserDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var userDao: UserDatabaseDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = db.userDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() {
        val user = User("test")
        userDao.insert(user)

        val testUser = userDao.getUser("test")
        assertEquals(user, testUser)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndUpdateUser() {
        val user = User("test")
        userDao.insert(user)

        user.save_date = "22/22/22"
        userDao.update(user)

        val testUser = userDao.getUser("test")

        assertEquals(user, testUser)
        assertEquals(testUser?.save_date, "22/22/22")
    }

//    @Test
//    @Throws(Exception::class)
//    fun insertUsersAndGetAllEmails() {
//        val user = User("test")
//        val user2 = User("test2")
//        val user3 = User("test3")
//        userDao.insert(user)
//        userDao.insert(user2)
//        userDao.insert(user3)
//
//        val users = userDao.getAllUsers()
//        Log.i("UserDatabaseTest", "users : $users")
//    }

}

