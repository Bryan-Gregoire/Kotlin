package g53735.mobg5.cryptop.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.database.CryptoDatabaseDao
import kotlinx.coroutines.launch

class CryptoFavoriteViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto

    private val _favorites = MutableLiveData<List<Crypto>>()

    val cryptos: LiveData<List<Crypto>>
        get() = _favorites

    private val _navigateToCryptoDetail = MutableLiveData<Long>()

    val navigateToCryptoDetail
        get() = _navigateToCryptoDetail

    init {
        viewModelScope.launch {
            _favorites.value = daoCrypto.getAllFavorites()
        }
    }

    fun onCryptoClicked(id: Long) {
        _navigateToCryptoDetail.value = id
    }

    fun onCryptoDetailNavigated() {
        _navigateToCryptoDetail.value = null
    }

}