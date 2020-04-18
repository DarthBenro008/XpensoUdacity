package com.benrostudios.xpenso.ui.addexpense;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddExpenses extends Fragment {
    @BindView(R.id.fake_expense)
    Button fake;

    @BindView(R.id.entity)
    TextView entity;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.income)
    RadioButton income;

    @BindView(R.id.expense)
    RadioButton expense;

    @BindView(R.id.cash)
    RadioButton cash;

    @BindView(R.id.online)
    RadioButton online;


    private AddExpensesViewModel mViewModel;
    private boolean validation;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static AddExpenses newInstance() {
        return new AddExpenses();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_expenses_fragment, container, false);
        ButterKnife.bind(this, v);
        setOnClick();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddExpensesViewModel.class);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

    }

    public void setOnClick(){
        fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation(){
        validation = true;
        if(entity.getText().toString().isEmpty()){
            entity.setError("Please fill in Entity!");
            validation = false;
        }
        if(amount.getText().toString().isEmpty() || amount.getText().toString().equals("0")){
            amount.setError("Amount should be more than 0!");
            validation = false;
        }
        if(validation){
            performUpsert();
        }
    }

    private void performUpsert(){
        Date currentTime = Calendar.getInstance().getTime();
        String incomeString = income.isChecked()? getResources().getString(R.string.income) :getResources().getString(R.string.expenditure);
        String modeString = cash.isChecked()? getResources().getString(R.string.cash):getResources().getString(R.string.online);
        Expenses newTransaction = new Expenses(entity.getText().toString(), Double.valueOf(amount.getText().toString()), incomeString, modeString, currentTime, desc.getText().toString());
        mViewModel.insertTransaction(newTransaction);
        mViewModel.pushData(newTransaction, currentTime.toString());
        Bundle bundle = new Bundle();
        bundle.putString("Transaction",newTransaction.getTime().toString());
        mFirebaseAnalytics.logEvent("Transaction",bundle);
        Navigation.findNavController(getView()).navigateUp();
    }

}
