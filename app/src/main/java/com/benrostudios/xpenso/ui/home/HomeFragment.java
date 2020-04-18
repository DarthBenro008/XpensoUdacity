package com.benrostudios.xpenso.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.adapters.HistoryAdapter;
import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.utils.SharedUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.addButton)
    FloatingActionButton button;

    @BindView(R.id.circular_progress)
    CircularProgressIndicator progressIndicator;

    @BindView(R.id.recent_transaction_recycler)
    RecyclerView recentRecycler;

    @BindView(R.id.textview_month_display)
    TextView monthDisplay;

    private HomeViewModel homeViewModel;

    private Boolean isFirstTime;
    private SharedUtils sharedUtils;
    private HistoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        setIsFirstTime();
        setProgressIndicator(100, 1000);
        setButtonUI();
        setRecentRecycler();
        setMonthUI();

        return root;
    }

    private void setProgressIndicator(long current, long max) {
        progressIndicator.setProgress(current, max);

    }

    private void setMonthUI() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        monthDisplay.setText(sdf.format(date));
    }

    private void setButtonUI() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_addExpenses);
            }
        });
    }

    private void setRecentRecycler() {
        homeViewModel.getRecentList().observe(getViewLifecycleOwner(), new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adapter = new HistoryAdapter(getContext(), expenses, R.string.title_home);
                updateRecycler(adapter);
            }
        });
    }

    private void updateRecycler(HistoryAdapter adapter) {
        recentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recentRecycler.setAdapter(adapter);
    }

    private void setIsFirstTime() {
        sharedUtils = new SharedUtils(getContext());
        isFirstTime = sharedUtils.retriveFirstTime();
        Log.d("this", "" + isFirstTime);
        if (isFirstTime) {
            homeViewModel.sync();
            sharedUtils.saveFirstTime(false);
        }
    }
}
