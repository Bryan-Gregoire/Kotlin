package g53735.mobg5.cryptop.crypto

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.Crypto
import org.w3c.dom.Text


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

@BindingAdapter("cryptoPriceString")
fun TextView.setCryptoPriceString(item: Crypto?) {
    item?.let {
        text = item.price.toString()
    }
}

@BindingAdapter("cryptoMarketCapString")
fun TextView.setMarketCapString(item: Crypto?) {
    item?.let {
        text = item.marketCap.toString()
    }
}

@BindingAdapter("cryptoMaxSupplyString")
fun TextView.setCryptoMaxSupplyString(item: Crypto?) {
    item?.let {
        if(it.maxSupply == null) {
            text = "--"
        } else {
            text = item.maxSupply.toString()
        }
    }
}

@BindingAdapter("cryptoCirculatingSupplyString")
fun TextView.setCryptoCirculatingSupplyString(item: Crypto?) {
    item?.let {
        text = item.circulatingSupply.toString()
    }
}

@BindingAdapter("cryptoTotalSupplyString")
fun TextView.setCryptoTotalSupplyString(item: Crypto?) {
    item?.let {
        text = item.totalSupply.toString()
    }
}

@BindingAdapter("cryptoVolumeString")
fun TextView.setCryptoVolumeString(item: Crypto?) {
    item?.let {
        text = item.volume.toString()
    }
}


@BindingAdapter("cryptoPriceChangeString")
fun TextView.setPriceChangeString(item: Crypto?) {
    item?.let {
        text = item.priceChange.toString()
    }
}