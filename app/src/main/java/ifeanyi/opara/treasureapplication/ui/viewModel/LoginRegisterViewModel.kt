package ifeanyi.opara.treasureapplication.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ifeanyi.opara.treasureapplication.mainRepository.TreasureRepository
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(private var repository: TreasureRepository) : ViewModel(){

    private var userMutableLiveData : MutableLiveData<FirebaseUser>
    private var loggedOutMutableLiveData : MutableLiveData<Boolean>

    init {
        userMutableLiveData = repository.getUserMutableLiveData()
        loggedOutMutableLiveData = repository.getLoggedOutMutableLiveData()
    }

    fun register(email : String, password : String){
        repository.register(email, password)
    }

    fun login(email : String, password : String){
        repository.login(email, password)
    }

    fun logout(){
        repository.logout()
    }

    fun getLoggedOutMutableLiveData() : MutableLiveData<Boolean>{
        return loggedOutMutableLiveData
    }

    fun getUserMutableLiveData() : MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

}