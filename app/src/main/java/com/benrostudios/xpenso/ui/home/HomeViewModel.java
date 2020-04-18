package com.benrostudios.xpenso.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.repo.ExpensesRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private ExpensesRepository repo;
    private LiveData<List<Expenses>> recentList;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        repo = new ExpensesRepository(application);
        recentList = repo.getRecentList();
    }

    public void sync(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getUid());
        databaseReference.keepSynced(true);
        databaseReference.orderByChild(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("this", dataSnapshot.toString());
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Expenses expenses = messageSnapshot.getValue(Expenses.class);
                    repo.upsert(expenses);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    LiveData<List<Expenses>> getRecentList(){
        return recentList;
    }
}