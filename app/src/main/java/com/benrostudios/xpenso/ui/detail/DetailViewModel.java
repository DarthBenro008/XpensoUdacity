package com.benrostudios.xpenso.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.repo.ExpensesRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class DetailViewModel extends AndroidViewModel {

    private ExpensesRepository repo;
    public DetailViewModel(@NonNull Application application) {
        super(application);
        repo = new ExpensesRepository(application);
    }

    public void delete(Expenses expenses){
        repo.delete(expenses);
        firebaseDelete(expenses.getTime());
    }

    private void firebaseDelete(Date time){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid()+"/"+time.toString());
        database.removeValue();
    }
}
