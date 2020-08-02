package com.softgeeks.doorstep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.PhoneAuthProvider;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.network.CallUtils;
import com.softgeeks.doorstep.utils.CountryData;

import java.util.concurrent.TimeUnit;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button btnSignUp;
    private TextView tvHaveAccount;
    private NavController actionSignUpInstance;
    private EditText etSignUpEmail, etSignUpUserName, etSignUpPhoneNO, etSignUpAddress, etSignUpPass, etSignUpCPass;
    Spinner spCountries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate (R.layout.fragment_register, container, false);
        initViews (view);
        return view;
    }

    private void initViews(View view) {
        btnSignUp=view.findViewById (R.id.btnSignUp);
        spCountries=view.findViewById (R.id.spCountries);

        etSignUpEmail=view.findViewById (R.id.etSignUpEmail);
        etSignUpUserName=view.findViewById (R.id.etSignUpUserName);
        etSignUpPhoneNO=view.findViewById (R.id.etSignUpPhoneNO);
        etSignUpAddress=view.findViewById (R.id.etSignUpAddress);
        etSignUpPass=view.findViewById (R.id.etSignUpPass);
        etSignUpCPass=view.findViewById (R.id.etSignUpCPass);

        tvHaveAccount=view.findViewById (R.id.tvHaveAccount);
        actionSignUpInstance=Navigation.findNavController (getActivity (), R.id.mainNavHostFragment);
        spCountries.setAdapter (new ArrayAdapter<String> (getContext (), android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        tvHaveAccount.setOnClickListener (this);
        btnSignUp.setOnClickListener (this);
    }

    private boolean validateData() {
        if (etSignUpEmail.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid email !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpUserName.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid user name !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpPhoneNO.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid phone no !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpAddress.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid address !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpPass.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid password !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpCPass.getText ().toString ().isEmpty ()) {
            Toast.makeText (getContext (), "Enter a valid password !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (!etSignUpPass.getText ().toString ().equalsIgnoreCase (etSignUpCPass.getText ().toString ())) {
            Toast.makeText (getContext (), "All password fields must match !!", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (etSignUpPhoneNO.getText ().toString ().trim ().length () < 10) {

            Toast.makeText (getContext (), "Enter a valid password !!", Toast.LENGTH_SHORT).show ();
            etSignUpPhoneNO.requestFocus ();
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.tvHaveAccount:
                actionSignUpInstance.navigate (R.id.action_signup_to_login);

                break;
            case R.id.btnSignUp:
                if (validateData ()) {
                    String code = CountryData.countryAreaCodes[spCountries.getSelectedItemPosition()];
                    String phoneNumber = "+" + code + etSignUpPhoneNO.getText ().toString ();
                   Bundle bundle=new Bundle ();
                   bundle.putString ("phoneNo",phoneNumber);
                   bundle.putString ("address",etSignUpAddress.getText ().toString ());
                   bundle.putString ("username",etSignUpUserName.getText ().toString ());
                   bundle.putString ("email",etSignUpEmail.getText ().toString ());
                    bundle.putString ("password",etSignUpPass.getText ().toString ());

                    actionSignUpInstance.navigate (R.id.action_signup_to_verify_phone,bundle);
               }

                break;
        }
    }


    }


