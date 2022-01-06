package g53735.mobg5.cryptop.crypto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.databinding.ListItemCryptoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class CryptoAdapter(
    val clickListener: CryptoListener,
    val favoriteListener: CryptoFavoriteListener
) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(CryptoDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val cryptoItem = getItem(position) as DataItem.CryptoItem
                holder.bind(cryptoItem.crypto, clickListener, favoriteListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Crypto>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.CryptoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.CryptoItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Crypto,
            clickListener: CryptoListener,
            favoriteListener: CryptoFavoriteListener
        ) {
            binding.crypto = item
            binding.clickListener = clickListener
            binding.favoriteListener = favoriteListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCryptoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CryptoDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class CryptoListener(val clickListener: (cryptoId: Long) -> Unit) {
    fun onClick(crypto: Crypto) = clickListener(crypto.cryptoId)
}

class CryptoFavoriteListener(val clickListener: (cryptoId: Long) -> Unit) {
    fun onClickFavorite(crypto: Crypto) = clickListener(crypto.cryptoId)
}

sealed class DataItem {
    abstract val id: Long

    data class CryptoItem(val crypto: Crypto) : DataItem() {
        override val id = crypto.cryptoId
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }
}