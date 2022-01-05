package g53735.mobg5.cryptop.crypto

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.Crypto


@BindingAdapter("cryptoNameString")
fun TextView.setCryptoNameString(item: Crypto?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("cryptoSymbolString")
fun TextView.setCryptoSymbolString(item: Crypto?) {
    item?.let {
        text = item.symbol
    }
}

@BindingAdapter("cryptoRankString")
fun TextView.setCryptoRankString(item: Crypto?) {
    item?.let {
        text = item.rank.toString()
    }
}

@BindingAdapter("cryptoApiStatus")
fun bindStatus(statusImageView: ImageView, status: CryptoApiStatus) {
    when (status) {
        CryptoApiStatus.LOADING -> {
            statusImageView.visibility =  View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        CryptoApiStatus.ERROR -> {
            statusImageView.visibility =  View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        CryptoApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

