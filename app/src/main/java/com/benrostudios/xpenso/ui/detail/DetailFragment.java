package com.benrostudios.xpenso.ui.detail;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;

import static com.benrostudios.xpenso.adapters.HistoryAdapter.PASS_TAG;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private Expenses expenses;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expenses = (Expenses) getArguments().getSerializable(PASS_TAG);
        Toast.makeText(getContext(),expenses.getPerson(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
