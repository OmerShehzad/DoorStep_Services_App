package com.softgeeks.doorstep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.network.CallUtils;
import com.softgeeks.doorstep.views.MainActivity;


public class LoginFragment extends Fragment implements View.OnClickListener {
    Button btnSigIn;
    NavController actionLoginInstance;
    TextView tvCreateAccount,tvSkipNow;
    EditText etEmailAddress, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_login, container, false);
        initViews (view);
        return view;
    }

    private void initViews(View view) {
        btnSigIn=view.findViewById (R.id.btnSigIn);
        tvSkipNow=view.findViewById (R.id.tvSkipNow);
        etPassword=view.findViewById (R.id.etPassword);
        etEmailAddress=view.findViewById (R.id.etEmailAddress);
        tvCreateAccount=view.findViewById (R.id.tvCreateAccount);
        actionLoginInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        btnSigIn.setOnClickListener (this);
        tvCreateAccount.setOnClickListener (this);
        tvSkipNow.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btnSigIn:
                if (validateData ()) {
                    new CallUtils ().uidLogin (actionLoginInstance, getContext (), etEmailAddress.getText ().toString (), etPassword.getText ().toString ());
                }
                break;
            case R.id.tvCreateAccount:
                actionLoginInstance.navigate (R.id.action_login_to_signup);
                break;
            case R.id.tvSkipNow:
                ((MainActivity)getActivity ()).mSessionStoreManager.saveBooleanData (getString (R.string.isLogin),true);
                ((MainActivity)getActivity ()).mSessionStoreManager.saveStringData (getString (R.string.loginSession),"guest");
                actionLoginInstance.navigate (R.id.action_login_to_home);
                break;
        }
    }

    private boolean validateData() {
        if (etEmailAddress.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid email !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etPassword.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid password !!", Toast.LENGTH_SHORT).show ();
            return false;
        }


        return true;
    }
}
