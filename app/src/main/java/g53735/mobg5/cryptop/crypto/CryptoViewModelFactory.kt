package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoViewModelFactory(private val dataCryptoDao: CryptoDatabaseDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            return CryptoViewModel(dataCryptoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}