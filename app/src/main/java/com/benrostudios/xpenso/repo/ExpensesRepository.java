package com.benrostudios.xpenso.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.db.ExpensesDAO;
import com.benrostudios.xpenso.db.ExpensesDatabase;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.util.Date;
import java.util.List;

public class ExpensesRepository {
    private ExpensesDAO expensesDAO;
    private LiveData<List<Expenses>> expensesList;
    private LiveData<List<Expenses>> recentList;
    private LiveData<Expenses> expense;


    public ExpensesRepository(@NotNull Application application) {

        ExpensesDatabase database = ExpensesDatabase.getInstance(application);
        expensesDAO = database.expensesDAO();
        expensesList = expensesDAO.loadAllTransactions();
        recentList = expensesDAO.getRecentTransactions();
    }

    public LiveData<List<Expenses>> getAllMovies() {
        return expensesList;
    }

    public LiveData<List<Expenses>> getRecentList(){return recentList;}

    public void upsert(Expenses expenses) {
        ExpensesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                expensesDAO.insertTransaction(expenses);
            }
        });
    }

    public void delete(Expenses expenses) {
        ExpensesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                expensesDAO.deleteTransaction(expenses);
            }
        });
    }

    public void nukeTable(){
        ExpensesDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                expensesDAO.nukeTable();
            }
        });
    }

    public LiveData<Expenses> favMovieCheck(Date date) {
        return expensesDAO.getTransaction(date);
    }
}
