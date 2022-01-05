package g53735.mobg5.cryptop.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val METHOD = "X-CMC_PRO_API_KEY"
private const val API_KEY = "cd418b42-f9e8-4d08-8928-822175943f76"

private const val BASE_URL = "https://pro-api.coinmarketcap.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CryptoApiService {

    @Headers(
        "$METHOD: $API_KEY",
        "Accept: application/json"
    )
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getProperties(
        @Query("limit") limit: Int,
        @Query("convert") convert: String
    ): CryptosData
}

object CryptoAPI {
    val retrofitService: CryptoApiService by lazy {
        retrofit.create(CryptoApiService::class.java)
    }
}

