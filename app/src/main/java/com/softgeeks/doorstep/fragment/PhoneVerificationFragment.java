package com.softgeeks.doorstep.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.network.CallUtils;

import java.util.concurrent.TimeUnit;
//This fragment is used for phone no verification which we get from registration fragment
public class PhoneVerificationFragment extends Fragment implements View.OnClickListener {
    ImageView ivBackEmailVerification;
    TextInputEditText etUIDPasscode;
    TextView tvUIDResendCode, tvUIDChangeEmail;
    ProgressBar emailVerificationProgressBar;
    private FirebaseAuth mAuth;
    private String verificationId;
    Button btnVerify;
    String phoneNo="",address="",username="",email="",password="";
    private NavController actionProfileVerificationInstance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_phone_verification, container, false);
        //initviews is used to initialize the views
        initViews (view);
        actionProfileVerificationInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        //get bundles data get the data sent from previous fragment
        getBundlesData ();
        return view;
    }

    private void getBundlesData() {
        Bundle bundle=this.getArguments ();
        phoneNo=bundle.getString ("phoneNo", "");
        address=bundle.getString ("address", "");
        username=bundle.getString ("username", "");
        email=bundle.getString ("email", "");
        password=bundle.getString ("password", "");

//send verification code method sent a code generated from firebase to specific phone no
        sendVerificationCode (phoneNo);
    }

    private void initViews(View view) {

        mAuth=FirebaseAuth.getInstance ();
        ivBackEmailVerification=view.findViewById (R.id.ivBackEmailVerification);
        etUIDPasscode=view.findViewById (R.id.etUIDPasscode);
        btnVerify=view.findViewById (R.id.btnVerify);
        tvUIDResendCode=view.findViewById (R.id.tvUIDResendCode);
        tvUIDChangeEmail=view.findViewById (R.id.tvUIDChangeEmail);
        emailVerificationProgressBar=view.findViewById (R.id.emailVerificationProgressBar);
        emailVerificationProgressBar.setVisibility (View.GONE);
        ivBackEmailVerification.setOnClickListener (this);
        tvUIDChangeEmail.setOnClickListener (this);
        btnVerify.setOnClickListener (this);
        tvUIDResendCode.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {

            // btnverify will automatically verifies the data if code in edit text will be empty than it prompt to user to enter code otherwise it verifies first
            case R.id.btnVerify:
                String code=etUIDPasscode.getText ().toString ().trim ();

                if (code.isEmpty () || code.length () < 6) {

                    etUIDPasscode.setError ("Enter code...");
                    etUIDPasscode.requestFocus ();
                    return;
                }

                verifyCode(code);
                break;
            case R.id.tvUIDChangeEmail:
                //if user want to change email than he can press chaange email text and it moves towards registration screen again
                actionProfileVerificationInstance.navigate (R.id.action_profile_to_register);
                break;
            case R.id.tvUIDResendCode:
                //if user want to generate a new verification code than he can press resend code option and code will be resent to him
                // on text message as well
                sendVerificationCode (phoneNo);
                break;


        }

    }

    private void sendVerificationCode(String number) {
        emailVerificationProgressBar.setVisibility (View.VISIBLE);
        //this code verifies phone no and send code to mobile
        PhoneAuthProvider.getInstance ().verifyPhoneNumber (
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }


//remember i use firebase as a sending medium for generating and sending a new code so in this method phone no first verified and than a 6 digit code will be generated
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent (s, forceResendingToken);
            verificationId=s;
        }




//on verification of phone no completed code will be sent through this method and verified in verify code method
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode ();
            if (code != null) {
                etUIDPasscode.setText (code);
                verifyCode (code);
            }
        }

        //on verification failed i display a toast message which displays error happened there
        @Override
        public void onVerificationFailed(FirebaseException e) {
            emailVerificationProgressBar.setVisibility (View.GONE);
            Toast.makeText (getContext (), e.getMessage (), Toast.LENGTH_LONG).show ();
        }
    };

// on success of sign in with credentials what should be done is defined here
    //we call signup method which can store data in db  of new user
    //if code is not true than a toast message will be displaying showing error on it
    public void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
emailVerificationProgressBar.setVisibility (View.GONE);
                            new CallUtils ().uidSignUp (phoneNo, address, address, address, username, address, actionProfileVerificationInstance, getContext (), email, password);


                        } else {
                            emailVerificationProgressBar.setVisibility (View.GONE);
                            Toast.makeText(getContext (), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
}
