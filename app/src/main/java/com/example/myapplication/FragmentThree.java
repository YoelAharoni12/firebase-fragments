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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment {
    private FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText emailTextEl;
    private EditText passwordTextEl;
    private EditText nameTextEl;
    private EditText phoneTextEl;

    public FragmentThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentThree newInstance(String param1, String param2) {
        FragmentThree fragment = new FragmentThree();
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