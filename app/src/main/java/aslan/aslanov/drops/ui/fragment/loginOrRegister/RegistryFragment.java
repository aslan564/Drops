package aslan.aslanov.drops.ui.fragment.loginOrRegister;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import aslan.aslanov.drops.R;
import aslan.aslanov.drops.databinding.FragmentRegistryBinding;
import aslan.aslanov.drops.util.BaseFragment;


public class RegistryFragment extends BaseFragment {
    private static final String TAG = "RegistryFragment";
    private FirebaseAuth mAuth;
    private FragmentRegistryBinding fragmentRegistryBinding;


    public RegistryFragment() {
        // Required empty public constructor
    }

    public static RegistryFragment newInstance(String param1, String param2) {
        RegistryFragment fragment = new RegistryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegistryBinding = FragmentRegistryBinding.inflate(inflater, container, false);
        setLayout(fragmentRegistryBinding);
        return fragmentRegistryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        fragmentRegistryBinding.buttonRegistry.setOnClickListener(view1 -> {
            String email = fragmentRegistryBinding.editTextUsername.getText().toString();
            String password = fragmentRegistryBinding.editTextPassword.getText().toString();
            if (email.isEmpty()) {
                fragmentRegistryBinding.editTextUsername.setError(getString(R.string.errorMessage));
            } else if (password.isEmpty()) {
                fragmentRegistryBinding.editTextPassword.setError(getString(R.string.errorMessage));
            } else {

                createUser(email, password, view);
            }
        });
    }

    private void createUser(String email, String password, View view) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Navigation.findNavController(view).navigate(RegistryFragmentDirections.actionNavigationChatRegistryToNavigationChatFragment());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    createToast("Authentication failed.");
                    updateUI(null);
                }
            }
        }).addOnCanceledListener(() -> createToast("Registry cancelled")).addOnFailureListener(e -> createToast("Registry failure"));
    }

    private void createToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}