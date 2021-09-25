package ifeanyi.opara.treasureapplication.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.ByteArrayOutputStream
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel : LoginRegisterViewModel by activityViewModels()

    private lateinit var drawable: Drawable

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var IV : ByteArray = ByteArray(12)
        var random : SecureRandom = SecureRandom()
        random.nextBytes(IV)

        _binding = FragmentHomeBinding.bind(view)

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

            encryptBtn.setOnClickListener {
                try {
                    val encrypt =  viewModel.encrypt(homeImg)
                    Log.d("TestTwo", encrypt)

                    encryptText.text = encrypt
                } catch (e : java.lang.Exception){
                    e.printStackTrace()
                }
            }

            decryptBtn.setOnClickListener {
                try {
                    decryptText.text = viewModel.decrypt()
                    homeImg2.setImageBitmap(viewModel.loadImage())

                } catch (e: Exception) {
                    e.printStackTrace()
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


}