package com.benrostudios.xpenso.ui.addexpense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.repo.ExpensesRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddExpensesViewModel extends AndroidViewModel {

    private ExpensesRepository repo;
    private DatabaseReference mDatabase;
    private String uid = FirebaseAuth.getInstance().getUid();

    public AddExpensesViewModel(@NonNull Application application) {
        super(application);

         repo = new ExpensesRepository(application);

    }

    public void insertTransaction(Expenses expenses){repo.upsert(expenses);}

    public void pushData(Expenses expenses, String time){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uid).child(time).setValue(expenses);
    }

}
