package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.database.CryptoDatabaseDao
import g53735.mobg5.cryptop.network.CryptoAPI
import kotlinx.coroutines.launch

enum class CryptoApiStatus { LOADING, ERROR, DONE }

enum class CryptoDatabaseLimit(val value: Int) {
    SHOW_TOP_100(100),
    SHOW_TOP_200(200),
    SHOW_TOP_500(500)
}

class CryptoViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto

    private val _cryptos = MutableLiveData<List<Crypto>>()

    val cryptos: LiveData<List<Crypto>>
        get() = _cryptos

    private val _status = MutableLiveData<CryptoApiStatus>()

    val status: LiveData<CryptoApiStatus>
        get() = _status

    private val _navigateToCryptoDetail = MutableLiveData<Long>()

    val navigateToCryptoDetail
        get() = _navigateToCryptoDetail

    private val _navigateToFavoritesCrypto = MutableLiveData<Boolean>()

    val navigateToFavoritesCrypto
        get() = _navigateToFavoritesCrypto

    private val _cryptoFavorite = MutableLiveData<Long>()

    val cryptoFavorite
        get() = _cryptoFavorite


    init {
        getCryptoProperties()
        getCryptoTop(CryptoDatabaseLimit.SHOW_TOP_100)
    }

    fun onCryptoClicked(id: Long) {
        _navigateToCryptoDetail.value = id
    }

    fun onCryptoDetailNavigated() {
        _navigateToCryptoDetail.value = null
    }

    fun onGoToFavorite() {
        _navigateToFavoritesCrypto.value = true
    }

    fun doneNavigateToFavorite() {
        _navigateToFavoritesCrypto.value = false
    }

    fun onFavoriteClicked(id: Long) {
        _cryptoFavorite.value = id
    }

    private fun getCryptoProperties() {

        viewModelScope.launch {
            _status.value = CryptoApiStatus.LOADING
            try {
                daoCrypto.clear()

                val cryptoResult = CryptoAPI.retrofitService.getProperties(5000, "USD")
                var cryptosList = mutableListOf<Crypto>()

                var ind: Long = 0
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

                    val crypto = Crypto(
                        ind, cmcId, name, symbol, price, rank, marketCap,
                        maxSupply, circulatingSupply, totalSupply, volume, priceChange
                    )
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

    fun onClickFavorite(id: Long) {
        viewModelScope.launch {
            _cryptos.value?.get(id.toInt())?.favorite =
                _cryptos.value?.get(id.toInt())?.favorite != true
            _cryptos.value?.get(id.toInt())?.let { daoCrypto.update(it) }
        }
    }

    private fun getCryptoTop(limit: CryptoDatabaseLimit) {
        viewModelScope.launch {
            _cryptos.value = daoCrypto.getTopCryptos(limit.value)
        }
    }

    fun updateLimit(limit: CryptoDatabaseLimit) {
        getCryptoTop(limit)
    }
}