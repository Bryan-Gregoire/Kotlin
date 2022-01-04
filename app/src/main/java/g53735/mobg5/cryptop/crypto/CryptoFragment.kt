package g53735.mobg5.cryptop.crypto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.CryptoDatabase
import g53735.mobg5.cryptop.databinding.FragmentCryptoBinding

class CryptoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCryptoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_crypto, container, false)

        val application = requireNotNull(this.activity).application

        val cryptoDao = CryptoDatabase.getInstance(application).cryptoDatabaseDao
        val viewModelFactory = CryptoViewModelFactory(cryptoDao)

        val cryptoViewModel = ViewModelProvider(this, viewModelFactory).get(CryptoViewModel::class.java)

        binding.cryptoViewModel = cryptoViewModel

        val adapter = CryptoAdapter(CryptoListener { cryptoId ->
            cryptoViewModel.onCryptoClicked(cryptoId)
        })
        binding.cryptoList.adapter = adapter

        cryptoViewModel.cryptos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner

        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> 3
                else -> 1
            }
        }

        binding.cryptoList.layoutManager = manager

        return binding.root
    }
}