package g53735.mobg5.cryptop.crypto

import android.widget.TextView
import androidx.databinding.BindingAdapter
import g53735.mobg5.cryptop.database.Crypto


@BindingAdapter("cryptoNameString")
fun TextView.setCryptoNameString(item: Crypto?) {
    item?.let {
        text = item.name
    }
}