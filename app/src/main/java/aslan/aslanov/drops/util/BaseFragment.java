package aslan.aslanov.drops.util;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import aslan.aslanov.drops.R;

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected ViewDataBinding viewDataBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setLifecycleOwner(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + viewDataBinding);
    }

    public BaseFragment() {
        Log.d(TAG, "BaseFragment: " + viewDataBinding);
    }

    public void setLayout(ViewDataBinding viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " + viewDataBinding);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onCreateView: " + viewDataBinding);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + viewDataBinding);
    }
}
