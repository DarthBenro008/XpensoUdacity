package com.benrostudios.xpenso.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.benrostudios.xpenso.repo.ExpensesRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ExpensesRepository repo;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repo = new ExpensesRepository(application);
    }

    public void nukeTable(){
        repo.nukeTable();
    }
}