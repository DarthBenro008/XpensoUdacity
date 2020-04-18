package com.benrostudios.xpenso.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.repo.ExpensesRepository;

import java.lang.reflect.Executable;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private ExpensesRepository repo;
    private LiveData<List<Expenses>> expenses;

    public HistoryViewModel(@NonNull Application application) {
        super(application);

        repo = new ExpensesRepository(application);
        expenses = repo.getAllMovies();

    }

    LiveData<List<Expenses>> getExpenses() {
        return expenses;
    }


}