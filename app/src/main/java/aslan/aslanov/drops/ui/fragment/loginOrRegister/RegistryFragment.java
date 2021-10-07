package aslan.aslanov.drops.ui.fragment.loginOrRegister;

import static aslan.aslanov.drops.util.SharedManager.getLoginStatus;
import static aslan.aslanov.drops.util.SharedManager.setLoginOrRegister;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import aslan.aslanov.drops.util.BaseViewModelFactory;
import aslan.aslanov.drops.util.RequestStatus;


public class RegistryFragment extends BaseFragment {
    private static final String TAG = "RegistryFragment";
    private FragmentRegistryBinding fragmentRegistryBinding;
    private RegistryViewModel registryViewModel;
    private AlertDialog dialog;


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
        updateUI();
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
        BaseViewModelFactory factory = new BaseViewModelFactory();
        registryViewModel = new ViewModelProvider(getViewModelStore(), factory).get(RegistryViewModel.class);
        observeRegistry(view);
        dialog = createDialog();
        fragmentRegistryBinding.buttonRegistry.setOnClickListener(view1 -> {
            String email = fragmentRegistryBinding.editTextUsername.getText().toString();
            String password = fragmentRegistryBinding.editTextPassword.getText().toString();
            if (email.isEmpty()) {
                fragmentRegistryBinding.editTextUsername.setError(getString(R.string.errorMessage));
            } else if (password.isEmpty()) {
                fragmentRegistryBinding.editTextPassword.setError(getString(R.string.errorMessage));
            } else {

                registryViewModel.registerOrLogin(email, password, requireContext());
            }
        });
        fragmentRegistryBinding.textViewRegisterOrLogin.setOnClickListener(view12 -> {

            if (getLoginStatus(requireContext())) {
                setLoginOrRegister(requireContext(), false);
                Log.d(TAG, "updateUI-----------------: 1 " + getLoginStatus(requireContext()));
                fragmentRegistryBinding.textViewRegisterOrLogin.setText(getString(R.string.register));
                fragmentRegistryBinding.buttonRegistry.setText(getString(R.string.register));
            } else {
                setLoginOrRegister(requireContext(), true);
                Log.d(TAG, "updateUI-----------------: 2 " + getLoginStatus(requireContext()));
                fragmentRegistryBinding.textViewRegisterOrLogin.setText(getString(R.string.login));
                fragmentRegistryBinding.buttonRegistry.setText(getString(R.string.login));
            }
        });
    }

    private void observeRegistry(View view) {

        registryViewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        registryViewModel.requestStatusLiveData.observe(getViewLifecycleOwner(), new Observer<RequestStatus>() {
            @Override
            public void onChanged(RequestStatus requestStatus) {
                if (requestStatus != null) {
                    switch (requestStatus) {
                        case ERROR:
                            Log.d(TAG, "onChanged: " + Log.ERROR);
                            dialog.cancel();
                            break;
                        case LOADING:
                            //show progress
                            dialog.show();
                            break;
                        case SUCCESS:
                            dialog.dismiss();
                            Navigation.findNavController(view).navigate(RegistryFragmentDirections.actionNavigationChatRegistryToNavigationChatFragment());
                            break;
                    }
                }
            }
        });
    }

    private void updateUI() {

        if (getLoginStatus(requireContext())) {
            fragmentRegistryBinding.textViewRegisterOrLogin.setText(getString(R.string.login));
            fragmentRegistryBinding.buttonRegistry.setText(getString(R.string.login));
        } else {
            fragmentRegistryBinding.textViewRegisterOrLogin.setText(getString(R.string.register));
            fragmentRegistryBinding.buttonRegistry.setText(getString(R.string.register));
        }
    }

    public AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(getString(R.string.loading))
                .setCancelable(false)
                .setTitle(getString(R.string.wait));
        return builder.create();
    }
    private void createToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}