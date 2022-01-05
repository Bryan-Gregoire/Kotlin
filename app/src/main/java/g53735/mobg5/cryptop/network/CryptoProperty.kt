package g53735.mobg5.cryptop.network

import com.squareup.moshi.Json

// Crypto Currencies

data class CryptosData (
    val data: List<CryptoProperty>?
)

data class CryptoProperty(
    val name: String,
    val symbol: String,
    val id: Int,
    val quote: Quote,
    @Json(name = "cmc_rank") val rank: Int,
    @Json(name = "max_supply") val maxSupply: Double?,
    @Json(name = "circulating_supply") val circulatingSupply: Double?,
    @Json(name = "total_supply") val totalSupply: Double?,
    )

data class Quote(
    @Json(name = "USD") val usd: Currency?
)

data class Currency(
    val price: Double?,
    @Json(name = "market_cap") val marketCap: Double?,
    @Json(name = "volume_24h") val volume_24h: Double?,
    @Json(name = "percent_change_24h") val priceChangePercentage: Double?
)


