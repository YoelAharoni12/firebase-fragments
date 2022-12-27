package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class main_fragment extends Fragment {
    private EditText searchFiled;
    private TextView nameBox;
    private TextView emailtBox;
    private TextView phonetBox;
    private TextView passwordtBox;

    public main_fragment() {
    }


    public static main_fragment newInstance(String param1, String param2) {
        main_fragment fragment = new main_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        Button registerToApp = view.findViewById(R.id.searchBtn);
        searchFiled = view.findViewById(R.id.searchFiled);
        nameBox = view.findViewById(R.id.nameBox);
        emailtBox = view.findViewById(R.id.emailBox);
        passwordtBox = view.findViewById(R.id.passwordBox);
        phonetBox = view.findViewById(R.id.phoneBox);
        registerToApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
            }
        });
        return view;
    }

    public void read() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(searchFiled.getText().toString());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Person value = dataSnapshot.getValue(Person.class);
                if (value == null) {
                    Toast.makeText(getActivity(), "Failed to read value.", Toast.LENGTH_LONG).show();
                    return;
                }
                emailtBox.setText(value.email);
                nameBox.setText(value.name);
                passwordtBox.setText(value.password);
                phonetBox.setText(value.phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to read value.", Toast.LENGTH_LONG).show();
                Log.w("result", "Failed to read value.", error.toException());
            }
        });
    }
}