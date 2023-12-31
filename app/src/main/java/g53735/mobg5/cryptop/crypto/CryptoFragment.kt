package g53735.mobg5.cryptop.crypto

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.CryptoDatabase
import g53735.mobg5.cryptop.databinding.FragmentCryptoBinding

class CryptoFragment : Fragment() {

    private lateinit var cryptoViewModel: CryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCryptoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_crypto, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application

        val cryptoDao = CryptoDatabase.getInstance(application).cryptoDatabaseDao
        val viewModelFactory = CryptoViewModelFactory(cryptoDao)

        cryptoViewModel = ViewModelProvider(this, viewModelFactory)[CryptoViewModel::class.java]

        binding.cryptoViewModel = cryptoViewModel

        val adapter = CryptoAdapter(CryptoListener { cryptoId ->
            cryptoViewModel.onCryptoClicked(cryptoId)
        }, CryptoFavoriteListener { cryptoId ->
            cryptoViewModel.onFavoriteClicked(cryptoId)
        })
        binding.cryptoList.adapter = adapter

        cryptoViewModel.cryptos.observe(viewLifecycleOwner, {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        cryptoViewModel.cryptoFavorite.observe(viewLifecycleOwner, {
            it?.let {
                cryptoViewModel.onClickFavorite(it)
            }
        })

        cryptoViewModel.navigateToCryptoDetail.observe(viewLifecycleOwner, { crypto ->
            crypto?.let {
                this.findNavController().navigate(
                    CryptoFragmentDirections
                        .actionCryptoFragmentToCryptoDetailFragment(crypto)
                )
                cryptoViewModel.onCryptoDetailNavigated()
            }
        })

        cryptoViewModel.navigateToFavoritesCrypto.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    CryptoFragmentDirections
                        .actionCryptoFragmentToCryptoFavoriteFragment()
                )
                cryptoViewModel.doneNavigateToFavorite()
            }
        })

        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> 3
                else -> 1
            }
        }

        binding.cryptoList.layoutManager = manager

        cryptoViewModel.updateLimit(CryptoDatabaseLimit.SHOW_TOP_100)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cryptoViewModel.updateLimit(
            when (item.itemId) {
                R.id.show_top_100 -> CryptoDatabaseLimit.SHOW_TOP_100
                R.id.show_top_200 -> CryptoDatabaseLimit.SHOW_TOP_200
                R.id.show_top_500 -> CryptoDatabaseLimit.SHOW_TOP_500
                else -> CryptoDatabaseLimit.SHOW_TOP_100
            }
        )
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crypto_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}