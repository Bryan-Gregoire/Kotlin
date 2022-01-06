package g53735.mobg5.cryptop.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoFavoriteViewModelFactory(private val dataCryptoDao: CryptoDatabaseDao) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoFavoriteViewModel::class.java)) {
            return CryptoFavoriteViewModel(dataCryptoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}