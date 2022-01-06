package g53735.mobg5.cryptop.crypto

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.Crypto


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

@BindingAdapter("setFavoriteImage")
fun setFavoriteImage(favoriteImageView: ImageView, favorite: Boolean) {
    if(favorite) {
        favoriteImageView.setImageResource(R.drawable.favorite)
    } else {
        favoriteImageView.setImageResource(R.drawable.not_favorite)
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
        text = context.getString(R.string.display_price, item.price)
    }
}

@BindingAdapter("cryptoMarketCapString")
fun TextView.setMarketCapString(item: Crypto?) {
    item?.let {
        text = String.format("$%,.0f", item.marketCap)
    }
}

@BindingAdapter("cryptoMaxSupplyString")
fun TextView.setCryptoMaxSupplyString(item: Crypto?) {
    item?.let {
        if(it.maxSupply == null) {
            text = "--"
        } else {
            text = String.format("%,.0f ${it.symbol}", item.maxSupply)
        }
    }
}

@BindingAdapter("cryptoCirculatingSupplyString")
fun TextView.setCryptoCirculatingSupplyString(item: Crypto?) {
    item?.let {
        text = String.format("%,.2f ${it.symbol}", item.circulatingSupply)
    }
}

@BindingAdapter("cryptoTotalSupplyString")
fun TextView.setCryptoTotalSupplyString(item: Crypto?) {
    item?.let {
        text = String.format("%,.0f ${it.symbol}", item.totalSupply)
    }
}

@BindingAdapter("cryptoVolumeString")
fun TextView.setCryptoVolumeString(item: Crypto?) {
    item?.let {
        text =String.format("$%,.2f", item.volume)
    }
}


@BindingAdapter("cryptoPriceChangeString")
fun TextView.setPriceChangeString(item: Crypto?) {
    item?.let {
        text = context.getString(R.string.display_percentage, "%", item.priceChange)
        if(it.priceChange!! < 0) {
            setTextColor(Color.RED)
        } else if(it.priceChange!! > 0) {
            setTextColor(Color.GREEN)
        }
    }
}