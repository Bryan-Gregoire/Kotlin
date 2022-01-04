package g53735.mobg5.cryptop.connection

import android.content.Context
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g53735.mobg5.cryptop.R
import g53735.mobg5.cryptop.database.UserDatabase
import g53735.mobg5.cryptop.databinding.FragmentConnectionBinding


class ConnectionFragment : Fragment() {

    private lateinit var binding: FragmentConnectionBinding

    private lateinit var connectionViewModel: ConnectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_connection, container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataUserDao = UserDatabase.getInstance(application).userDatabaseDao
        val connectionViewModelFactory = ConnectionViewModelFactory(dataUserDao)

        connectionViewModel =
            ViewModelProvider(this, connectionViewModelFactory)
                .get(ConnectionViewModel::class.java)

        connectionViewModel.eventConnection.observe(viewLifecycleOwner, { if(it) tryToLogIn() })

        binding.connectionViewModel = connectionViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        )

        adapter.addAll(connectionViewModel.getAllUsers().toString())
        binding.emailEditText.setAdapter(adapter)

        connectionViewModel.getAllUsers().observe(viewLifecycleOwner) { users ->
            adapter.clear()
            adapter.addAll(users.groupBy { it.email }.keys)
        }

        connectionViewModel.navigateToCrypto.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                this.findNavController().navigate(
                    ConnectionFragmentDirections.actionConnectionFragmentToCryptoFragment())
                connectionViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    private fun tryToLogIn() {
        isValidEmail()
        connectionViewModel.onConnectionComplete()
    }

    private fun isValidEmail() {
        val email = binding.emailEditText.text.toString()
        if (!EMAIL_ADDRESS.matcher(email).matches()) {
            displayToast("Email invalide", "red")
        } else {
            displayToast("Email valide", "green")
            connectionViewModel.addUser(email)
            connectionViewModel.onConnect()
        }
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.connectionButton.windowToken, 0)
        binding.emailEditText.clearFocus()
    }

    private fun displayToast(message: String, color: String) {
        Toast.makeText(
            context,
            HtmlCompat.fromHtml(
                "<font color='$color'> <b> $message </b> </font> ",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ), Toast.LENGTH_LONG
        ).show()
    }
}