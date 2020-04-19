package com.benrostudios.xpenso.ui.home;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.benrostudios.xpenso.utils.TransactionUtils;
import com.benrostudios.xpenso.widget.NewAppWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import antonkozyriatskyi.circularprogressindicator.PatternProgressTextAdapter;
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

    @BindView(R.id.textview_income)
    TextView income;

    @BindView(R.id.textview_expenses)
    TextView expenses;

    @BindView(R.id.textview_balance)
    TextView balance;

    private HomeViewModel homeViewModel;

    private Boolean isFirstTime;
    private SharedUtils sharedUtils;
    private HistoryAdapter adapter;
    private double expense;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        sharedUtils = new SharedUtils(getContext());
        updateIncomeExpensesUI();
        setIsFirstTime();
        setButtonUI();
        setRecentRecycler();
        setMonthUI();

        return root;
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
                updateSharedPreference(expenses);
                sendBroadcastToWidget();
            }
        });
    }

    private void updateRecycler(HistoryAdapter adapter) {
        recentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recentRecycler.setAdapter(adapter);
    }

    private void setIsFirstTime() {
        isFirstTime = sharedUtils.retriveFirstTime();
        Log.d("this", "" + isFirstTime);
        if (isFirstTime) {
            homeViewModel.sync();
            sharedUtils.saveFirstTime(false);
        }
    }
    private void updateSharedPreference(List<Expenses> expenses) {
        // Get a instance of SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // Get the editor object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the ingredient list and convert the list to string
        String ingredientString = TransactionUtils.toExpensesString(expenses);
        editor.putString(getContext().getString(R.string.pref_transaction_list_key), ingredientString);
        editor.apply();
    }

    private void sendBroadcastToWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(), NewAppWidget.class));
        Intent updateAppWidgetIntent = new Intent();
        updateAppWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateAppWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        getContext().sendBroadcast(updateAppWidgetIntent);
    }

    private void updateIncomeExpensesUI(){
        income.setText(sharedUtils.retriveIncome());
        expenses.setText(sharedUtils.retriveExpense());
        double income = Double.parseDouble(sharedUtils.retriveIncome());
        expense = Double.parseDouble(sharedUtils.retriveExpense());
        double bal = income-expense;
        percentageMath(expense, income);
        balance.setText(String.valueOf(bal));
    }

    private void setProgressIndicator(long current, long max) {
        progressIndicator.setProgress(current, max);
        progressIndicator.setProgressTextAdapter(new PatternProgressTextAdapter("%.1f%%"));
        Log.d("ello",""+current);
    }

    private void percentageMath(double current , double max){
        double currento = (current/max)*100;
        long fin = Double.valueOf(currento).longValue();
        setProgressIndicator(fin, 100);
    }

}
