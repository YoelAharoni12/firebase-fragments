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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentTwo extends Fragment {
    private FirebaseAuth mAuth;
    // ...
// Initialize Firebase Auth
//    mAuth = FirebaseAuth.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Boolean validMail = false;
    private Boolean validPass = false;
    private EditText emailEl;
    private EditText passwordEl;


    public fragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentTwo newInstance(String param1, String param2) {
        fragmentTwo fragment = new fragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Button button23 = view.findViewById(R.id.loginToApp);
        emailEl = view.findViewById(R.id.emailLoginFiled);
        passwordEl = view.findViewById(R.id.passwordLoginFiled);
        button23.setEnabled(false);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailValidate2 = (EditText) view.findViewById(R.id.emailLoginFiled);
                login();
            }
        });


        EditText emailValidate = (EditText) view.findViewById(R.id.emailLoginFiled);


        emailValidate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = emailValidate.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!hasFocus) {
                    if (email.matches(emailPattern)) {
                        validMail = true;
                        if (validPass) {
                            button23.setEnabled(true);
                        }
                        Toast.makeText(getActivity(), "valid email address", Toast.LENGTH_LONG).show();
                    } else {
                        button23.setEnabled(false);
                        emailValidate.setError("Invalid email address");
                        Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        EditText passValidate = (EditText) view.findViewById(R.id.passwordLoginFiled);
        passValidate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String pass = passValidate.getText().toString().trim();
                String passPattern = "[0-9]+";

                if (!hasFocus) {
                    if (pass.matches(passPattern)) {
                        validPass = true;
                        if (validMail) {
                            button23.setEnabled(true);
                        }
                        Toast.makeText(getActivity(), "valid email address", Toast.LENGTH_LONG).show();
                    } else {
                        validPass = false;
                        button23.setEnabled(false);
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