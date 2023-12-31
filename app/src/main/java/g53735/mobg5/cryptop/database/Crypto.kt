package g53735.mobg5.cryptop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_table")
data class Crypto(

    @PrimaryKey
    var cryptoId: Long,

    @ColumnInfo
    var coinMarketCapId: Int,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var symbol: String,

    @ColumnInfo
    var price: Double?,

    @ColumnInfo
    var rank: Int,

    @ColumnInfo(name = "market_cap")
    var marketCap: Double?,

    @ColumnInfo(name = "max_supply")
    var maxSupply: Double?,

    @ColumnInfo(name = "circulating_supply")
    var circulatingSupply: Double?,

    @ColumnInfo(name = "total_supply")
    var totalSupply: Double?,

    @ColumnInfo(name = "volume_24h")
    var volume: Double?,

    @ColumnInfo(name = "price_change_percentage_24h")
    var priceChange: Double?,

    @ColumnInfo
    var favorite: Boolean = false

//    @ColumnInfo
//    var category: String,  // MISS
//
//    @ColumnInfo
//    var description: String, // MISS
//
//    @ColumnInfo(name = "logo_url") // MISS
//    var logo: String,


    )
