package ifeanyi.opara.treasureapplication.repositoryImplementation

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ifeanyi.opara.treasureapplication.mainRepository.TreasureRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreasureRepositoryImplementation(private val application: Application) : TreasureRepository {


    private var firebaseUser : MutableLiveData<FirebaseUser> = MutableLiveData()
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    //private var mAuth : MutableLiveData<FirebaseAuth> = MutableLiveData()
    private var loggedOutMutableLiveData : MutableLiveData<Boolean> = MutableLiveData()

    init {

        if (firebaseAuth.currentUser != null){
            firebaseUser.postValue(firebaseAuth.currentUser)
            loggedOutMutableLiveData.postValue(false)
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor, { task ->
                if (task.isSuccessful) {
                    firebaseUser.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(
                        application,
                        "Registration Failed" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }



    @RequiresApi(Build.VERSION_CODES.P)
    override fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor, { task ->
                if (task.isSuccessful){
                    firebaseUser.postValue(firebaseAuth.currentUser)
                }else{
                    Toast.makeText(
                        application,
                        "Login Failed" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun logout() {
        firebaseAuth.signOut()
        loggedOutMutableLiveData.postValue(true)
    }

    override fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return firebaseUser
    }

    override fun getLoggedOutMutableLiveData(): MutableLiveData<Boolean> {
        return loggedOutMutableLiveData
    }
}