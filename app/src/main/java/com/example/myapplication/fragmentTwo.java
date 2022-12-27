package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;


public class fragmentTwo extends Fragment {
    private FirebaseAuth mAuth;
    private Boolean validMail = false;
    private Boolean validPass = false;
    private EditText emailEl;
    private EditText passwordEl;
    private String passPattern = "[0-9]+";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public fragmentTwo() {
    }

    public static fragmentTwo newInstance(String param1, String param2) {
        fragmentTwo fragment = new fragmentTwo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Button loginBtn = view.findViewById(R.id.loginToApp);

        emailEl = view.findViewById(R.id.emailLoginFiled);
        passwordEl = view.findViewById(R.id.passwordLoginFiled);

        loginBtn.setEnabled(false);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        EditText emailValidate = view.findViewById(R.id.emailLoginFiled);

        emailValidate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = emailValidate.getText().toString().trim();

                if (!hasFocus) {
                    if (email.matches(emailPattern)) {
                        validMail = true;
                        if (validPass) {
                            loginBtn.setEnabled(true);
                        }
                        Toast.makeText(getActivity(), "valid email address", Toast.LENGTH_LONG).show();
                    } else {
                        loginBtn.setEnabled(false);
                        emailValidate.setError("Invalid email address");
                        Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        EditText passValidate =  view.findViewById(R.id.passwordLoginFiled);
        passValidate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String pass = passValidate.getText().toString().trim();

                if (!hasFocus) {
                    if (pass.matches(passPattern)) {
                        validPass = true;
                        if (validMail) {
                            loginBtn.setEnabled(true);
                        }
                        Toast.makeText(getActivity(), "valid email address", Toast.LENGTH_LONG).show();
                    } else {
                        validPass = false;
                        loginBtn.setEnabled(false);
                        passValidate.setError("Invalid email address");
                        Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public void login() {
        String email = emailEl.getText().toString();
        String password = passwordEl.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "login ok", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragmentTwo_to_main_fragment);
                } else {
                    Toast.makeText(getActivity(), "login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}