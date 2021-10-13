package aslan.aslanov.drops.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import aslan.aslanov.drops.repository.ChatMessageRepository;
import aslan.aslanov.drops.repository.RegistryRepository;
import aslan.aslanov.drops.ui.fragment.chat.ChatMessageViewModel;
import aslan.aslanov.drops.ui.fragment.loginOrRegister.RegistryViewModel;

public class BaseViewModelFactory implements ViewModelProvider.Factory {
    private final RegistryRepository registryRepository = new RegistryRepository();
    private final ChatMessageRepository chatMessageRepository = new ChatMessageRepository();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistryViewModel.class)) {
            return (T) new RegistryViewModel(registryRepository);
        } else if (modelClass.isAssignableFrom(ChatMessageViewModel.class)) {
            return (T) new ChatMessageViewModel(chatMessageRepository);
        } else {
            throw new IllegalArgumentException("Unknown Class ");
        }
    }
}
