package com.benrostudios.xpenso.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.Date;
import java.util.List;

@Dao
public interface ExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(Expenses expenses);

    @Delete
    void deleteTransaction(Expenses expenses);

    @Query("SELECT * FROM expenses")
    LiveData<List<Expenses>> loadAllTransactions();

    @Query("SELECT * FROM expenses WHERE time = :timeo")
    LiveData<Expenses> getTransaction(Date timeo);

    @Query("SELECT * FROM expenses ORDER BY time DESC LIMIT 5")
    LiveData<List<Expenses>> getRecentTransactions();

}
