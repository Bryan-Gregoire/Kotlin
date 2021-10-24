package g53735.mobg5.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import g53735.mobg5.myapplication.databinding.FragmentConnectionBinding

class ConnectionFragment : Fragment() {

    private lateinit var binding: FragmentConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentConnectionBinding>(
            inflater,
            R.layout.fragment_connection,
            container,
            false
        )

        binding.connectionButton.setOnClickListener {
            isValidEmail(binding.emailEditText.text.toString())
        }

        return binding.root
    }

    private fun isValidEmail(email: String) {
        if (!EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Email invalide", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Email valide", Toast.LENGTH_SHORT).show()
        }
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.connectionButton.windowToken, 0)
        binding.emailEditText.clearFocus()
    }
}