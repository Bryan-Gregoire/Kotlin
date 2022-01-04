package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto

    val cryptos = daoCrypto.getAllCryptos()

    private val _navigateToCryptoDetail = MutableLiveData<Long>()
    val navigateToSleepDetail
        get() = _navigateToCryptoDetail

    fun onCryptoClicked(id: Long) {
        _navigateToCryptoDetail.value = id
    }

    fun onCryptoDetailNavigated() {
        _navigateToCryptoDetail.value = null
    }
}