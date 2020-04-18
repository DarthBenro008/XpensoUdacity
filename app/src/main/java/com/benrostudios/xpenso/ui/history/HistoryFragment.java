package com.benrostudios.xpenso.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.adapters.HistoryAdapter;
import com.benrostudios.xpenso.db.Expenses;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment {
    @BindView(R.id.history_recycler)
    RecyclerView historyRecycler;

    private HistoryViewModel historyViewModel;
    private HistoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, root);
        historyViewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                if (expenses.size() == 0) {
                    Toast.makeText(getContext(),R.string.no_transactions ,Toast.LENGTH_LONG).show();
                } else {
                    Collections.reverse(expenses);
                    adapter = new HistoryAdapter(getContext(), expenses,R.string.title_history);
                    updateRecycler(adapter);
                }
            }
        });
        return root;
    }

    public void updateRecycler(HistoryAdapter adapter) {
        historyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        historyRecycler.setAdapter(adapter);
    }
}
