package aslan.aslanov.drops.repository;

import static aslan.aslanov.drops.util.SharedManager.getLoginStatus;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import aslan.aslanov.drops.ui.fragment.loginOrRegister.RegistryFragmentDirections;
import aslan.aslanov.drops.util.BaseViewModelFactory;
import aslan.aslanov.drops.util.RequestStatus;

public class RegistryRepository {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final MutableLiveData<RequestStatus> _RequestStatusMutableLiveData = new MutableLiveData<>();
    public LiveData<RequestStatus> requestStatusLiveData = get_RequestStatusMutableLiveData();

    private final MutableLiveData<String> _message = new MutableLiveData<>();
    public LiveData<String> message = _message;

    private MutableLiveData<RequestStatus> get_RequestStatusMutableLiveData() {
        return _RequestStatusMutableLiveData;
    }

    private void set_RequestStatusMutableLiveData(RequestStatus status) {
        this._RequestStatusMutableLiveData.setValue(status);
    }

    public void createUser(String email, String password, Context context) {

        set_RequestStatusMutableLiveData(RequestStatus.LOADING);
        if (getLoginStatus(context)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                _message.setValue(null);
                set_RequestStatusMutableLiveData(RequestStatus.SUCCESS);
            }).addOnFailureListener(e -> {
                _message.setValue(e.getMessage());
                set_RequestStatusMutableLiveData(RequestStatus.ERROR);
            });
        }{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        _message.setValue(null);
                        set_RequestStatusMutableLiveData(RequestStatus.SUCCESS);
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Navigation.findNavController(view).navigate(RegistryFragmentDirections.actionNavigationChatRegistryToNavigationChatFragment());
                    } else {
                        // If sign in fails, display a message to the user.
                        set_RequestStatusMutableLiveData(RequestStatus.ERROR);
                    }
                }
            }).addOnCanceledListener(() -> {
                set_RequestStatusMutableLiveData(RequestStatus.ERROR);
                _message.setValue("Request Failed");
            }).addOnFailureListener(e -> {
                set_RequestStatusMutableLiveData(RequestStatus.ERROR);
                _message.setValue(e.getMessage());
            });
        }



    }

}
