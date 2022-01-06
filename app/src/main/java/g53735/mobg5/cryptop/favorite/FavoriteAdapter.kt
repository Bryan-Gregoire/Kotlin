package g53735.mobg5.cryptop.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.Crypto
import g53735.mobg5.cryptop.databinding.ListItemFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class FavoriteAdapter(val clickListener: FavoriteListener) :
    ListAdapter<DataFavoriteItem, RecyclerView.ViewHolder>(CryptoFavoriteDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteViewHolder -> {
                val cryptoItem = getItem(position) as DataFavoriteItem.CryptoItem
                holder.bind(cryptoItem.crypto, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> FavoriteViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Crypto>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataFavoriteItem.Header)
                else -> listOf(DataFavoriteItem.Header) + list.map { DataFavoriteItem.CryptoItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataFavoriteItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataFavoriteItem.CryptoItem -> ITEM_VIEW_TYPE_ITEM
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

    class FavoriteViewHolder private constructor(val binding: ListItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Crypto, clickListener: FavoriteListener) {
            binding.crypto = item
            binding.clickFavoriteListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoriteBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }
    }
}

class CryptoFavoriteDiffCallback : DiffUtil.ItemCallback<DataFavoriteItem>() {

    override fun areItemsTheSame(oldItem: DataFavoriteItem, newItem: DataFavoriteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataFavoriteItem, newItem: DataFavoriteItem): Boolean {
        return oldItem == newItem
    }
}

class FavoriteListener(val clickListener: (cryptoId: Long) -> Unit) {
    fun onClick(crypto: Crypto) = clickListener(crypto.cryptoId)
}

sealed class DataFavoriteItem {
    abstract val id: Long

    data class CryptoItem(val crypto: Crypto) : DataFavoriteItem() {
        override val id = crypto.cryptoId
    }

    object Header : DataFavoriteItem() {
        override val id = Long.MIN_VALUE
    }
}
