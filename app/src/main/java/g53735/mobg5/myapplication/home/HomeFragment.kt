package g53735.mobg5.myapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g53735.mobg5.myapplication.R
import g53735.mobg5.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        viewModelFactory =
            HomeViewModelFactory(HomeFragmentArgs.fromBundle(requireArguments()).emailLogin)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel

        return binding.root
    }
}