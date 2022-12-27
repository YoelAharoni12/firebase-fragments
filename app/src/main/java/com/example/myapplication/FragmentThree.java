package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentThree extends Fragment {
    private FirebaseAuth mAuth;


    private EditText emailTextEl;
    private EditText passwordTextEl;
    private EditText nameTextEl;
    private EditText phoneTextEl;

    public FragmentThree() {
    }


    public static FragmentThree newInstance(String param1, String param2) {
        FragmentThree fragment = new FragmentThree();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        Button registerToApp = view.findViewById(R.id.registerToApp);
        emailTextEl = view.findViewById(R.id.emailToRegister);
        passwordTextEl = view.findViewById(R.id.passForRegister);
        nameTextEl = view.findViewById(R.id.nameToRegister);
        phoneTextEl = view.findViewById(R.id.phoneToRegister);

        registerToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
        return view;
    }

    public void register(View view) {
        String email = emailTextEl.getText().toString();
        String password = passwordTextEl.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "register successful", Toast.LENGTH_LONG).show();
                    write(view);
                    Navigation.findNavController(requireView()).navigate(R.id.action_fragmentThree_to_main_fragment);

                } else {
                    Toast.makeText(getActivity(), "register failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void write(View view) {
        String email = emailTextEl.getText().toString();
        String password = passwordTextEl.getText().toString();
        String name = nameTextEl.getText().toString();
        String phone = phoneTextEl.getText().toString();
        Person person = new Person(name, phone, email, password);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(person.name).setValue(person);
    }
}