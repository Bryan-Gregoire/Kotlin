package g53735.mobg5.cryptop.crypto

import androidx.lifecycle.ViewModel
import g53735.mobg5.cryptop.database.CryptoDatabaseDao

class CryptoViewModel(dataDaoCrypto: CryptoDatabaseDao) : ViewModel() {

    val daoCrypto = dataDaoCrypto
}