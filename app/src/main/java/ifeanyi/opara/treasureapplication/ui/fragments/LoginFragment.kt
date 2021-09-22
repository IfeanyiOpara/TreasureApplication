package ifeanyi.opara.treasureapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ifeanyi.opara.treasureapplication.R
import ifeanyi.opara.treasureapplication.databinding.FragmentLoginBinding
import ifeanyi.opara.treasureapplication.ui.viewModel.LoginRegisterViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginRegisterViewModel by activityViewModels()// attaches it to the parent activity hosting it

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        viewModel.getUserMutableLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("Login Fragment", "${it == null}")
            if (it != null){
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }

        })

        binding.apply {
            loginBtn.setOnClickListener {
                if(validateEmail() && validatePassword()){
                    val email = loginEmail.editText?.text.toString().trim()
                    val password = loginPassword.editText?.text.toString().trim()
                    viewModel.login(email, password)
                }
            }

            loginReg.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToFragmentRegister()
                findNavController().navigate(action)
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email: String = binding.loginEmail.editText!!.text.toString().trim()

        return if (email.isEmpty()) {
            binding.loginEmail.error = "Field cannot be empty"
            false
        } else {
            binding.loginEmail.error = null
            binding.loginEmail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val email: String = binding.loginPassword.editText!!.text.toString().trim()

        return if (email.isEmpty()) {
            binding.loginPassword.error = "Field cannot be empty"
            false
        } else {
            binding.loginPassword.error = null
            binding.loginPassword.isErrorEnabled = false
            true
        }
    }

}