package aslan.aslanov.drops.ui.fragment.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import aslan.aslanov.drops.model.TextMessage;
import aslan.aslanov.drops.repository.ChatMessageRepository;

public class ChatMessageViewModel extends ViewModel {
    private final ChatMessageRepository chatMessageRepository;

    public LiveData<TextMessage> messageData;

    public ChatMessageViewModel(ChatMessageRepository repository) {
        chatMessageRepository = repository;
        messageData = chatMessageRepository.listLiveData;
    }

    public void getDummyMessage(TextMessage message) {
        chatMessageRepository.addDummyMessage(message);
    }
}
