package com.benrostudios.xpenso.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.utils.TransactionUtils;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // Creates and returns a ListRemoteViewsFactory
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private List<Expenses> expensesList;

        public ListRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        // Called on start and when notifyAppWidgetViewDataChanged is called
        @Override
        public void onDataSetChanged() {
            // Get the updated transaction string from shared preferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String ingredientString = sharedPreferences.getString(getString(R.string.pref_transaction_list_key), "");

            // Convert transaction string to the list of ingredients
            expensesList = TransactionUtils.toExpensesList(ingredientString);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (expensesList == null) return 0;
            return expensesList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (expensesList == null || expensesList.size() == 0) return null;

            Expenses expenses = expensesList.get(position);
            // Extract the transaction details
            double amount = expenses.getAmount();
            String person = expenses.getPerson();
            String date = expenses.getTime().toString();
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            String prefix = expenses.getType().equals(mContext.getResources().getString(R.string.expenditure))?"-":"+";
            views.setTextViewText(R.id.amount_widget, prefix+amount);
            views.setTextViewText(R.id.person_widget, person);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}
