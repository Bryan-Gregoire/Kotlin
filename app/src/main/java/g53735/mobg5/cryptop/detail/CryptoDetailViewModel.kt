package g53735.mobg5.cryptop.detail

import androidx.lifecycle.*
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoDetailViewModel(private val cryptoKey: Long = 0L, dataDao: CryptoDatabaseDao) :
    ViewModel() {

    val cryptoDao = dataDao

    private val selectedCrypto : LiveData<Crypto> = cryptoDao.getCrypto(cryptoKey)

    fun getCrypto() = selectedCrypto
}