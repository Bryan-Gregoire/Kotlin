package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g53735.mobg5.cryptop.database.CryptoDatabaseDao
import g53735.mobg5.cryptop.network.CryptoAPI
import kotlinx.coroutines.launch

class CryptoViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto

    val cryptos = daoCrypto.getAllCryptos()

//    private val _response = MutableLiveData<String>()
//
//    // The external immutable LiveData for the response String
//    val response: LiveData<String>
//        get() = _response

    private val _navigateToCryptoDetail = MutableLiveData<Long>()
    val navigateToSleepDetail
        get() = _navigateToCryptoDetail

    init {
//        getCryptoProperties()
    }

    fun onCryptoClicked(id: Long) {
        _navigateToCryptoDetail.value = id
    }

    fun onCryptoDetailNavigated() {
        _navigateToCryptoDetail.value = null
    }

//    private fun getCryptoProperties() {
//        viewModelScope.launch {
//            try {
//                val listResult = CryptoAPI.retrofitService.getProperties(3, "USD")
//                _response.value = "Success: ${listResult.data?.size}"
//
//                var contain: String = ""
//
//                listResult.data!!.forEach { data ->
//                        contain += "Market cap ${data.symbol} : ${data.quote.usd?.marketCap} "
//                }
//                _response.value = contain
//
//            } catch (e: Exception) {
//                _response.value = "Failure: ${e.message}"
//            }
//        }
//    }
}