package com.benrostudios.xpenso.ui.auth.signup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.benrostudios.xpenso.R;
import com.benrostudios.xpenso.ui.auth.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {
    private FirebaseAuth mAuth;
    private SwitchToSignIn mCallback;
    private boolean validation;
    private final static String TAG = "SignUp";
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.gotosingin)
    Button gotosignin;

    public interface SwitchToSignIn{
        void switchtosignin();
        void authenticated();
    }

    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mCallback = (SwitchToSignIn) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement SwitchToSignIn");
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this ,v);
        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.switchtosignin();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        return v;
    }

    private void validation(){
        validation = true;
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Please Fill in a valid email");
            validation = false;
        }
        if(password.getText().toString().length() < 8){
            password.setError("Password must be more than 8 characters");
            validation = false;
        }
        if(validation){
            SignUp(email.getText().toString(),password.getText().toString());
        }
    }

    private void SignUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mCallback.authenticated();
                            Toast.makeText(getContext(), "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
}
