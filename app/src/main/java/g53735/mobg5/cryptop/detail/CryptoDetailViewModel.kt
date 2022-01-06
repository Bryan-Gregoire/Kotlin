package g53735.mobg5.cryptop.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.database.CryptoDatabaseDao
import kotlinx.coroutines.launch

class CryptoDetailViewModel(private val cryptoKey: Long = 0L, dataDao: CryptoDatabaseDao) :
    ViewModel() {

    val cryptoDao = dataDao

    private val selectedCrypto : LiveData<Crypto> = cryptoDao.getCrypto(cryptoKey)

    fun getCrypto() = selectedCrypto
}