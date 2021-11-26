package g53735.mobg5.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_login")
data class Login(

    @PrimaryKey()
    var email: String = "",

    @ColumnInfo()
    var save_date: LocalDateTime = LocalDateTime.now(),
)
