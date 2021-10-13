package aslan.aslanov.drops.ui.fragment.chat;

import static aslan.aslanov.drops.util.UtilConstant.RECEIVER;
import static aslan.aslanov.drops.util.UtilConstant.SENDER;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import aslan.aslanov.drops.R;
import aslan.aslanov.drops.databinding.FragmentChatBinding;
import aslan.aslanov.drops.model.TextMessage;
import aslan.aslanov.drops.ui.fragment.chat.adapter.ChatMessageAdapter;
import aslan.aslanov.drops.util.BaseFragment;
import aslan.aslanov.drops.util.BaseViewModelFactory;

public class ChatFragment extends BaseFragment {

    private FragmentChatBinding binding;
    private FirebaseAuth mAuth;
    private ChatMessageAdapter messageAdapter;
    private ChatMessageViewModel chatMessageViewModel;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        setLayout(binding);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final NavController navController = Navigation.findNavController(view);
        messageAdapter = new ChatMessageAdapter();

        updateUI(currentUser, navController);
        BaseViewModelFactory factory = new BaseViewModelFactory();
        chatMessageViewModel = new ViewModelProvider(getViewModelStore(), factory).get(ChatMessageViewModel.class);


        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        final int[] i = {0};
        binding.buttonSendMessage.setOnClickListener(view1 -> {
            String message = binding.editTextMessage.getText().toString();
            if (!message.isEmpty()) {
                TextMessage textMessage = new TextMessage(message, Calendar.getInstance().getTime(), RECEIVER, "Aslanov Aslan", "aslan@gmail.com");
                chatMessageViewModel.getDummyMessage(textMessage);
                Log.d("adghaksdak", "onClick: " + message);
                binding.editTextMessage.setText("");
            }

        });
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        TextMessage message = new TextMessage("salam aleykum", Calendar.getInstance().getTime(), SENDER, "Aslanov Aslan", "aslan@gmail.com");
        chatMessageViewModel.getDummyMessage(message);
        observeMessage();
    }

    private void observeMessage() {
        chatMessageViewModel.messageData.observe(getViewLifecycleOwner(), new Observer<TextMessage>() {
            @Override
            public void onChanged(TextMessage message) {
                if (message != null) {
                    HashMap<Date, TextMessage> textMessageHashMap = new HashMap<>();
                    textMessageHashMap.put(message.getDate(), message);
                    messageAdapter.addMessageItemToList(textMessageHashMap);
                    extracted();
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void extracted() {
        binding.recyclerViewMessage.setAdapter(messageAdapter);
        binding.recyclerViewMessage.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMessage.scrollToPosition(messageAdapter.getItemCount() - 1);
    }

    private void updateUI(FirebaseUser currentUser, NavController controller) {
        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getUid();
        } else {
            // burasi islemedi duzeltmek lazimdir
            controller.navigate(R.id.navigation_chat_registry);
        }
    }


}