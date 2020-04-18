package com.benrostudios.xpenso.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;
import com.benrostudios.xpenso.ui.history.HistoryFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder> {
    private Context mContext;
    private List<Expenses> expenses;
    private int origin;
    public static final String PASS_TAG = "pass_tag";

    public HistoryAdapter(Context mContext, List<Expenses> expenses,int origin) {
        this.mContext = mContext;
        this.expenses = expenses;
        this.origin = origin;
    }


    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemViewHolder holder, int position) {
        holder.amount.setText(""+expenses.get(position).getAmount());
        holder.withdrawer.setText(expenses.get(position).getPerson());
        holder.time.setText(converter(expenses.get(position).getTime()));
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PASS_TAG,(Serializable) expenses.get(position));
                if(origin == R.string.title_history){
                    Navigation.findNavController(v).navigate(R.id.passaction, bundle);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_detail_fragment, bundle);
                }
            }
        });
        if(expenses.get(position).getType().equals(mContext.getResources().getString(R.string.expenditure))){
                holder.image.setImageResource(R.drawable.ic_attach_money_black_24dp);
                holder.image.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.red_money)));


        }else{
            holder.image.setImageResource(R.drawable.ic_attach_money_black_24dp);
            holder.image.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.green_money)));
            Log.d("hello",expenses.get(position).getType()+" "+mContext.getResources().getString(R.string.expenditure));
        }

    }

    public String converter(Date date){
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd MMM, h:mmaa");
        return sdf.format(date);
    }
    @Override
    public int getItemCount() {
        return expenses.size();
    }

    static class HistoryItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.withdrawer)
        TextView withdrawer;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.history_item_holder)
        CardView itemContainer;
        @BindView(R.id.incoming_image)
        ImageView image;

        HistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
