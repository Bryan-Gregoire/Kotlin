package g53735.mobg5.cryptop.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoDetailViewModelFactory(
    private val cryptoKey: Long,
    private val dataDao: CryptoDatabaseDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoDetailViewModel::class.java)) {
            return CryptoDetailViewModel(cryptoKey, dataDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}