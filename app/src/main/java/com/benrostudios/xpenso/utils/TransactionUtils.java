package com.benrostudios.xpenso.utils;

import com.benrostudios.xpenso.db.Expenses;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class TransactionUtils {
    public static List<Expenses> toExpensesList(String ingredientString) {
        if (ingredientString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Expenses>>() {}.getType();
        return gson.fromJson(ingredientString, listType);
    }


    public static String toExpensesString(List<Expenses> ingredientList) {
        if (ingredientList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Expenses>>() {}.getType();
        return gson.toJson(ingredientList, listType);
    }
}
