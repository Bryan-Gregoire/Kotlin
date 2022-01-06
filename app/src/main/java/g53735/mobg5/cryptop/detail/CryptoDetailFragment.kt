package g53735.mobg5.cryptop.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.CryptoDatabase
import g53735.mobg5.cryptop.databinding.FragmentCryptoDetailBinding

class CryptoDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCryptoDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_crypto_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = CryptoDetailFragmentArgs.fromBundle(requireArguments())

        val cryptoDao = CryptoDatabase.getInstance(application).cryptoDatabaseDao

        val cryptoDetailViewModelFactory =
            CryptoDetailViewModelFactory(arguments.cryptoKey, cryptoDao)

        val cryptoDetailViewModel = ViewModelProvider(this, cryptoDetailViewModelFactory
        ).get(CryptoDetailViewModel::class.java)

        binding.viewModel = cryptoDetailViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}