package g53735.mobg5.cryptop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_login")
data class User(

    @PrimaryKey
    var email: String = "",

    @ColumnInfo
    var save_date: String = LocalDateTime.now().toString(),
)
