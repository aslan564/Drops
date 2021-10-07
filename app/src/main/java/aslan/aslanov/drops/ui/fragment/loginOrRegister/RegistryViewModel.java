package aslan.aslanov.drops.ui.fragment.loginOrRegister;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import aslan.aslanov.drops.repository.RegistryRepository;
import aslan.aslanov.drops.util.RequestStatus;

public class RegistryViewModel extends ViewModel {

    private final RegistryRepository repository;
    public LiveData<RequestStatus> requestStatusLiveData;
    public LiveData<String> errorMessage;


    public RegistryViewModel(RegistryRepository registryRepository) {
        repository = registryRepository;
        requestStatusLiveData= repository.requestStatusLiveData;
        errorMessage= repository.message;
    }


    public void registerOrLogin(String email, String password,Context context) {
        repository.createUser(email, password,context);
    }


}
