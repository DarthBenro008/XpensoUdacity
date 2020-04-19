package com.benrostudios.xpenso.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.ui.auth.Auth;
import com.benrostudios.xpenso.utils.SharedUtils;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FirebaseAuth mFirebaseAuth;
    private SharedUtils utils;

    @BindView(R.id.logout_settings)
    Button logout;

    @BindView(R.id.username_profile)
    TextView profilename;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        utils = new SharedUtils(getContext());
        ButterKnife.bind(this, root);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.nukeSharedPrefs();
                profileViewModel.nukeTable();
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getContext(), Auth.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        profilename.setText(utils.retriveName());

        return root;
    }

}
