package ifeanyi.opara.treasureapplication.mainRepository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface TreasureRepository {


    fun login(email : String, password: String)

    fun register(email : String, password: String)

    fun logout()

    fun getUserMutableLiveData() : MutableLiveData<FirebaseUser>

    fun getLoggedOutMutableLiveData() : MutableLiveData<Boolean>

}