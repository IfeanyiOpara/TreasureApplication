package ifeanyi.opara.treasureapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ifeanyi.opara.treasureapplication.R
import ifeanyi.opara.treasureapplication.databinding.FragmentRegisterBinding
import ifeanyi.opara.treasureapplication.ui.viewModel.LoginRegisterViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: LoginRegisterViewModel by activityViewModels()// attaches it to the parent activity hosting it

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterBinding.bind(view)

        viewModel.getUserMutableLiveData().observe(viewLifecycleOwner, {
            if (it != null) {
                val action = RegisterFragmentDirections.actionFragmentRegisterToHomeFragment()
                findNavController().navigate(action)
            }
        })

        binding.apply {

            registerBtn.setOnClickListener{
                if (validateUsername() && validateEmail() && validatePassword() && validateConfirmPassword()){
                    val email = registerEmail.editText?.text.toString().trim()
                    val password = registerConfirmPassword.editText?.text.toString().trim()
                    viewModel.register(email, password)
                }
            }

            registerLogin.setOnClickListener {
                val action = RegisterFragmentDirections.actionFragmentRegisterToLoginFragment()
                findNavController().navigate(action)
            }
        }


    }

    private fun validateEmail(): Boolean {
        val email: String = binding.registerEmail.editText!!.text.toString().trim()

        return if (email.isEmpty()) {
            binding.registerEmail.error = "Field cannot be empty"
            false
        } else {
            binding.registerEmail.error = null
            binding.registerEmail.isErrorEnabled = false
            true
        }
    }

    private fun validateUsername(): Boolean {
        val name: String = binding.registerUsername.editText!!.text.toString().trim()

        return if (name.isEmpty()) {
            binding.registerUsername.error = "Field cannot be empty"
            false
        } else {
            binding.registerUsername.error = null
            binding.registerUsername.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password: String = binding.registerPassword.editText!!.text.toString().trim()

        return if (password.isEmpty()) {
            binding.registerPassword.error = "Field cannot be empty"
            false
        } else {
            binding.registerPassword.error = null
            binding.registerPassword.isErrorEnabled = false
            true
        }
    }

    private fun validateConfirmPassword(): Boolean {
        val confirmPassword: String = binding.registerConfirmPassword.editText!!.text.toString().trim()
        val password: String = binding.registerPassword.editText!!.text.toString().trim()
        val check : Boolean = confirmPassword == password || password == confirmPassword

        return if (confirmPassword.isEmpty()) {
            binding.registerConfirmPassword.error = "Field cannot be empty"
            false
        }else if(!check){
            binding.registerConfirmPassword.error = "Password doesn't match"
            false
        }
        else {
            binding.registerConfirmPassword.error = null
            binding.registerConfirmPassword.isErrorEnabled = false
            true
        }
    }

}