package com.benrostudios.xpenso.db;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.benrostudios.xpenso.utils.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({DateConverter.class})
@Database(entities = Expenses.class , version = 1, exportSchema = false)
public abstract class ExpensesDatabase extends RoomDatabase {
    public abstract ExpensesDAO expensesDAO();

    public static final String TAG = ExpensesDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "expenses_database";

    private static volatile ExpensesDatabase expensesRoomInstance;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ExpensesDatabase getInstance(Context context)
    {
        if (expensesRoomInstance == null)
        {
            synchronized(ExpensesDatabase.class) {
                if (expensesRoomInstance == null) {
                    Log.d(TAG, "Creating new database instance");
                    expensesRoomInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ExpensesDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        Log.d(TAG, "Getting the database instance");
        return expensesRoomInstance;
    }


}
