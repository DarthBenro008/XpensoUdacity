package com.benrostudios.xpenso.ui.auth.income;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.utils.SharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    private SetupCall mCallBack = null;
    private boolean validation;
    private SharedUtils utils;
    @BindView(R.id.usr_input)
    EditText user;
    @BindView(R.id.income_input)
    EditText income;
    @BindView(R.id.xpense_button)
    Button xpense;


    public interface SetupCall{
        public void setUpCompleted();
    }

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mCallBack = (SetupCall) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement SetupCall");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_income, container, false);
        ButterKnife.bind(this, v);
        utils = new SharedUtils(getContext());
        xpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(user.getText().toString(),income.getText().toString());
            }
        });
        return v;
    }

    public void validation(String userinp, String amount){
        validation = true;
        if(userinp.isEmpty() || userinp.length()>20){
            user.setError(getResources().getString(R.string.enter_valid_username));
            validation = false;
        }
        if(amount.isEmpty() || amount.equals("0")){
            income.setError(getResources().getString(R.string.enter_valid_amount));
            validation = false;
        }
        if(validation){
            utils.updateIncome(Double.valueOf(amount).longValue());
            utils.setUsername(userinp);
            mCallBack.setUpCompleted();
        }


    }
}
