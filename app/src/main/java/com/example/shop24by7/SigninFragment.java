package com.example.shop24by7;

import android.content.Intent;
import android.graphics.Color;
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

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SigninFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private  EditText password;

    private ImageButton closebtn;
    private Button signinbtn;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private ProgressBar progressBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        dontHaveAnAccount = view.findViewById(R.id.dont_have_an_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        email=view.findViewById(R.id.sign_in_email);
        password=view.findViewById(R.id.sign_in_password);

        closebtn=view.findViewById(R.id.sign_in_closebtn);
        signinbtn=view.findViewById(R.id.sign_in_button);

        firebaseAuth=FirebaseAuth.getInstance();

        progressBar=view.findViewById(R.id.sign_in_progressbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignupFragment());
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
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
                checkinputs();
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
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkinputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.argb(255,255,255,255));
            }else{
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(55,255,255,255));
            }
        }else{
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(55,255,255,255));
        }
    }
    private void checkEmailandPassword(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=8){
                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(55,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mainIntent();
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            signinbtn.setEnabled(true);
                            signinbtn.setTextColor(Color.argb(255,255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
    private void mainIntent(){
        Intent mainIntent = new Intent(getActivity(),MainActivity2.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}