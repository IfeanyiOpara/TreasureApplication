package ifeanyi.opara.treasureapplication.ui.viewModel

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ifeanyi.opara.treasureapplication.mainRepository.TreasureRepository
import ifeanyi.opara.treasureapplication.util.Encrypt
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
        private var repository: TreasureRepository,
        private var encrypt: Encrypt
    ) : ViewModel(){

    private var userMutableLiveData : MutableLiveData<FirebaseUser>

    init {
        userMutableLiveData = repository.getUserMutableLiveData()
    }

    fun register(email : String, password : String){
        repository.register(email, password)
    }

    fun login(email : String, password : String){
        repository.login(email, password)
    }


    fun getUserMutableLiveData() : MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

    fun encrypt(imageView: ImageView) : String{
        return encrypt.encryptButtonClicked(imageView)
    }

    fun decrypt() : String{
        return encrypt.decryptButtonClicked()
    }

    fun loadImage() : Bitmap {
        return encrypt.convertByteArrayToBitmap()
    }

}