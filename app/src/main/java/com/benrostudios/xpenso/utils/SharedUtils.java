package com.benrostudios.xpenso.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    Context mContext;
    public static final String UID = "uid";
    public static final String FIRST_TIME = "first_time";
    public static final String INCOME_SHARE = "income_time";
    public static final String EXPENSE_SHARE = "expense_time";
    public static final String BUDGET_SHARE = "budget_time";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedUtils(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveCount(String uid) {
        editor.putString(UID, uid).commit();
    }

    public String retriveCount() {
        return sharedPreferences.getString(UID, null);
    }

    public void saveFirstTime(Boolean isFirstTime) {
        editor.putBoolean(FIRST_TIME, isFirstTime).commit();
    }

    public Boolean retriveFirstTime() {
        return sharedPreferences.getBoolean(FIRST_TIME, true);
    }

    public void updateIncome(long income){
        long currentIncome = sharedPreferences.getLong(INCOME_SHARE,0);
        currentIncome = currentIncome+income;
        editor.putLong(INCOME_SHARE,currentIncome).commit();
    }
    public String retriveIncome(){
        return String.valueOf(sharedPreferences.getLong(INCOME_SHARE,0));
    }

    public void updateExpense(long expense){
        long currentExpense = sharedPreferences.getLong(EXPENSE_SHARE,0);
        currentExpense = currentExpense+expense;
        editor.putLong(EXPENSE_SHARE,currentExpense).commit();
    }

    public String retriveExpense(){
        return String.valueOf(sharedPreferences.getLong(EXPENSE_SHARE,0));
    }

    public void decrementIncome(long income){
        long currentIncome = sharedPreferences.getLong(INCOME_SHARE,0);
        currentIncome = currentIncome-income;
        editor.putLong(INCOME_SHARE,currentIncome).commit();
    }

    public void decrementExpenses(long expense){
        long currentExpense = sharedPreferences.getLong(EXPENSE_SHARE,0);
        currentExpense = currentExpense-expense;
        editor.putLong(EXPENSE_SHARE,currentExpense).commit();
    }

    public void setUsername(String name){
        editor.putString(BUDGET_SHARE,name).commit();
    }
    public String retriveName(){
        return sharedPreferences.getString(BUDGET_SHARE,"User");
    }

    public void nukeSharedPrefs(){
        editor.clear().commit();
    }
}
