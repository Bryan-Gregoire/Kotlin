package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.database.CryptoDatabaseDao
import g53735.mobg5.cryptop.network.CryptoAPI
import kotlinx.coroutines.launch

enum class CryptoApiStatus {LOADING, ERROR, DONE}

class CryptoViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto

    val cryptos = daoCrypto.getTop100Cryptos()

    private val _status = MutableLiveData<CryptoApiStatus>()

    val status: LiveData<CryptoApiStatus>
        get() = _status

    private val _navigateToCryptoDetail = MutableLiveData<Long>()

    val navigateToSleepDetail
        get() = _navigateToCryptoDetail

    init {
        getCryptoProperties()
    }

    fun onCryptoClicked(id: Long) {
        _navigateToCryptoDetail.value = id
    }

    fun onCryptoDetailNavigated() {
        _navigateToCryptoDetail.value = null
    }

    private fun getCryptoProperties() {
        viewModelScope.launch {
            _status.value = CryptoApiStatus.LOADING
            try {
                daoCrypto.clear()

                val cryptoResult = CryptoAPI.retrofitService.getProperties(500, "USD")
                var cryptosList = mutableListOf<Crypto>()

                var ind : Long = 0
                cryptoResult.data!!.forEach { data ->
                    val name: String = data.name
                    val symbol: String = data.symbol
                    val cmcId: Int = data.id
                    val rank: Int = data.rank
                    val maxSupply: Double? = data.maxSupply
                    val circulatingSupply: Double? = data.circulatingSupply
                    val totalSupply: Double? = data.totalSupply
                    val marketCap: Double? = data.quote.usd!!.marketCap
                    val price: Double? = data.quote.usd.price
                    val priceChange: Double? = data.quote.usd.priceChangePercentage
                    val volume: Double? = data.quote.usd.volume_24h

                    val crypto = Crypto(ind, cmcId, name, symbol, price, rank, marketCap,
                        maxSupply,circulatingSupply, totalSupply, volume, priceChange)
                    cryptosList.add(crypto)
                    ind++
                }

                daoCrypto.insert(cryptosList)
                _status.value = CryptoApiStatus.DONE
            } catch (e: Exception) {
                _status.value = CryptoApiStatus.ERROR
            }
        }
    }
}