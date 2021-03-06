package com.example.shop24by7;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private ImageButton closebutton;
    private Button SignupBtn;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";





    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        alreadyHaveAnAccount=view.findViewById(R.id.already_have_an_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        email = view.findViewById(R.id.sign_up_email);
        fullName =view.findViewById(R.id.sign_up_fullname);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword =view.findViewById(R.id.sign_up_confirm_password);

        closebutton=view.findViewById(R.id.sin_up_closebtn);
        SignupBtn= view.findViewById(R.id.sign_up_button);

        progressBar=view.findViewById(R.id.signup_progressbar);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore= FirebaseFirestore.getInstance();



        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }

        });

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPassword();
            }
        });
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullName.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.length()>=8){
                    if(!TextUtils.isEmpty(confirmPassword.getText())){
                        SignupBtn.setEnabled(true);
                        SignupBtn.setTextColor(Color.argb(255,255,255,255));
                    }else{
                        SignupBtn.setEnabled(false);
                        SignupBtn.setTextColor(Color.argb(50,255,255,255));
                    }

                }
                else{
                    SignupBtn.setEnabled(false);
                    SignupBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else{
                SignupBtn.setEnabled(false);
                SignupBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }
        else{
            SignupBtn.setEnabled(false);
            SignupBtn.setTextColor(Color.argb(50,255,255,255));

        }
    }
    private void checkEmailandPassword(){

        Drawable customErrorIcon = getResources().getDrawable(R.mipmap.erroricon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());

        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                SignupBtn.setEnabled(false);
                SignupBtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<Object,String> userdata = new HashMap<>();
                            userdata.put("fullname",fullName.getText().toString());

                            firebaseFirestore.collection("USERS").add(userdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull  Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        mainIntent();
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        SignupBtn.setEnabled(true);
                                        SignupBtn.setTextColor(Color.argb(255,255,255,255));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(),error , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            SignupBtn.setEnabled(true);
                            SignupBtn.setTextColor(Color.argb(255,255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                confirmPassword.setError("Password dosen't matches!",customErrorIcon);
            }
        }else{
            email.setError("Invalid Email!",customErrorIcon);
        }
    }
    private void mainIntent(){
        Intent mainIntent = new Intent(getActivity(),MainActivity2.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}