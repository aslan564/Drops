package aslan.aslanov.drops.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import aslan.aslanov.drops.repository.RegistryRepository;
import aslan.aslanov.drops.ui.fragment.loginOrRegister.RegistryViewModel;

public class BaseViewModelFactory implements ViewModelProvider.Factory {
    private RegistryRepository repository =new RegistryRepository();
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistryViewModel.class)) {
            return (T) new RegistryViewModel(repository);
        }else {
            throw new IllegalArgumentException("Unknown Class ");
        }
    }
}
