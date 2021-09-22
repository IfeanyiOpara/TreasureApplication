package ifeanyi.opara.treasureapplication.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ifeanyi.opara.treasureapplication.R
import ifeanyi.opara.treasureapplication.databinding.FragmentHomeBinding
import ifeanyi.opara.treasureapplication.ui.viewModel.LoginRegisterViewModel
import ifeanyi.opara.treasureapplication.ui.viewModel.LogoutViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel : LoginRegisterViewModel by activityViewModels()

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        viewModel.getLoggedOutMutableLiveData().observe(viewLifecycleOwner, Observer { loggedOut ->
            Log.d("Home Fragment", "${loggedOut == true}")
            if (loggedOut) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                logout()
            }
        })

        binding.apply {
//            logoutBtn.setOnClickListener {
//                viewModel.logout()
//            }

//            browseBtn.setOnClickListener {
//                Intent(Intent.ACTION_OPEN_DOCUMENT).also {
//                    it.type = "*/*"
//                    startActivityForResult(it, 0)
//                }
//            }

            browseBtn.setOnClickListener {
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, 0)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0){
            //take data from files
            var uri = data?.data
            //put it in our image view
            binding.homeImg.setImageURI(uri)
        }
    }

    fun logout(){
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}