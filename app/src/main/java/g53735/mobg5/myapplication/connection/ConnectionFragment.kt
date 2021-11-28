package g53735.mobg5.myapplication.connection

import android.content.Context
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import g53735.mobg5.myapplication.databinding.FragmentConnectionBinding

import android.widget.ArrayAdapter




class ConnectionFragment : Fragment() {

    private lateinit var binding: FragmentConnectionBinding

    private lateinit var connectionViewModel: ConnectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            g53735.mobg5.myapplication.R.layout.fragment_connection,
            container,
            false
        )

        connectionViewModel = ViewModelProvider(this).get(ConnectionViewModel::class.java)

        connectionViewModel.eventConnection.observe(
            viewLifecycleOwner, { tryConnection -> if(tryConnection) tryToLogIn() })

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

        return binding.root
    }

    private fun tryToLogIn() {
        isValidEmail()
        connectionViewModel.onConnectionComplete()
    }

    private fun isValidEmail() {
        if (!EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()) {
            displayToast("Email invalide", "red")
        } else {
            displayToast("Email valide", "green")
            navToHomeWithEmail()
        }
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.connectionButton.windowToken, 0)
        binding.emailEditText.clearFocus()
    }

    private fun navToHomeWithEmail() {
        val action = ConnectionFragmentDirections.actionConnectionFragmentToHomeFragment()
        action.emailLogin = binding.emailEditText.text.toString()
        NavHostFragment.findNavController(this).navigate(action)
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