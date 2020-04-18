package com.benrostudios.xpenso.ui.detail;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.db.Expenses;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.benrostudios.xpenso.adapters.HistoryAdapter.PASS_TAG;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private Expenses expenses;

    @BindView(R.id.expense_detail)
    TextView expense;
    @BindView(R.id.entity_detail)
    TextView entity;
    @BindView(R.id.desc_detail)
    TextView desc;
    @BindView(R.id.payment_mode_detail)
    TextView payment_mode;
    @BindView(R.id.time_detail)
    TextView time;
    @BindView(R.id.transaction_type_detail)
    TextView transactionType;
    @BindView(R.id.delete_transaction)
    Button delete;
    @BindView(R.id.image_detail)
    ImageView image;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expenses = (Expenses) getArguments().getSerializable(PASS_TAG);
        populateUI();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.delete(expenses);
                Navigation.findNavController(v).navigateUp();
            }
        });
    }

    private void populateUI() {
        entity.setText(expenses.getPerson());
        expense.setText("" + expenses.getAmount());
        desc.setText(expenses.getDesc().isEmpty() ? "No Description Found" : expenses.getDesc());
        payment_mode.setText(expenses.getPaymentMode());
        transactionType.setText(expenses.getType());
        time.setText(dateFormatter(expenses.getTime()));
        entity.setText(expenses.getPerson());
        entity.setText(expenses.getPerson());
        if (expenses.getType().equals(getResources().getString(R.string.expenditure))) {
            image.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red_money)));
        } else {
            image.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_money)));
        }
    }

    private String dateFormatter(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa dd MMM/yyyy");
        return sdf.format(date);
    }

}
