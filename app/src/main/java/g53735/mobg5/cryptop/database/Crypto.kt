package g53735.mobg5.cryptop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_table")
data class Crypto(

    @PrimaryKey(autoGenerate = true)
    var cryptoId: Long = 0L,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var symbol: String,

    @ColumnInfo
    var price: Double,

    @ColumnInfo
    var rank: Int,

    @ColumnInfo(name = "market_cap")
    var marketCap: Double,

    @ColumnInfo(name = "max_supply")
    var maxSupply: Long,

    @ColumnInfo(name = "circulating_supply")
    var circulatingSupply: Long,

    @ColumnInfo(name = "total_supply")
    var totalSupply: Long,

    @ColumnInfo(name = "volume_24h")
    var volume: Double,

    @ColumnInfo(name = "price_change_percentage_24h")
    var priceChange: Double,

    @ColumnInfo
    var category: String,  // MISS

    @ColumnInfo
    var description: String, // MISS

    @ColumnInfo(name = "logo_url") // MISS
    var logo: String,

    @ColumnInfo
    var favorite: Boolean = false
    )
