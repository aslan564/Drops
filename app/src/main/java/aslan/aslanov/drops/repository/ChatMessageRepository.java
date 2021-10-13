package aslan.aslanov.drops.repository;

import static aslan.aslanov.drops.util.UtilConstant.MESSAGE_CHANNEL;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

import aslan.aslanov.drops.model.TextMessage;

public class ChatMessageRepository {
    private final HashMap<String,TextMessage> textMessages = new HashMap<>();
    private final MutableLiveData<TextMessage> _listMutableLiveData = new MutableLiveData<>();
    public LiveData<TextMessage> listLiveData = _listMutableLiveData;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public void addDummyMessage(TextMessage message) {
        textMessages.put(message.getDate().toString(),message);
        DocumentReference reference = firestore.collection(MESSAGE_CHANNEL).document(message.getDate().toString());


        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    error.printStackTrace();
                }else {
                    if (value != null) {
                            reference.set(message, SetOptions.merge());
                    }
                }
            }
        });
        _listMutableLiveData.setValue(message);
    }

}
