package g53735.mobg5.cryptop.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.CryptoDatabase
import g53735.mobg5.cryptop.databinding.FragmentCryptoFavoriteBinding

class CryptoFavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCryptoFavoriteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_crypto_favorite, container, false)

        val application = requireNotNull(this.activity).application
        val dao = CryptoDatabase.getInstance(application).cryptoDatabaseDao
        val viewModelFactory = CryptoFavoriteViewModelFactory(dao)

        val favoriteViewModel =
            ViewModelProvider(this, viewModelFactory)[CryptoFavoriteViewModel::class.java]

        binding.favoriteViewModel = favoriteViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = FavoriteAdapter(FavoriteListener { cryptoId ->
            favoriteViewModel.onCryptoClicked(cryptoId)
        })
        binding.favoriteList.adapter = adapter


        favoriteViewModel.cryptos.observe(viewLifecycleOwner, {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        favoriteViewModel.navigateToCryptoDetail.observe(viewLifecycleOwner, { crypto ->
            crypto?.let {
                this.findNavController().navigate(
                    CryptoFavoriteFragmentDirections.actionCryptoFavoriteFragmentToCryptoDetailFragment(
                        crypto
                    )
                )
                favoriteViewModel.onCryptoDetailNavigated()
            }
        })

        return binding.root
    }
}