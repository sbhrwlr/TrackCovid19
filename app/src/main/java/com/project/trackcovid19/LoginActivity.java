package com.project.trackcovid19;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    LottieAnimationView sendfab;
    FirebaseAuth mAuth;
    RelativeLayout otp_layout, phonelayout;
    TextView skip;
    EditText phone;
    Button verify;
    String vCode;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    EditText editText_one, editText_two, editText_three, editText_four, editText_five, editText_six;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sendfab = findViewById(R.id.sendfab);
        skip = findViewById(R.id.skip);
        phone = findViewById(R.id.phone);
        mAuth = FirebaseAuth.getInstance();
        otp_layout = findViewById(R.id.otp_layout);
        phonelayout = findViewById(R.id.phonelayout);
        verify = findViewById(R.id.verify);

        editText_one =findViewById(R.id.et_1);
        editText_two =findViewById(R.id.et_2);
        editText_three =findViewById(R.id.et_3);
        editText_four =findViewById(R.id.et_4);
        editText_four = findViewById(R.id.et_5);
        editText_five = findViewById(R.id.et_6);

        editText_one.addTextChangedListener(this);
        editText_two.addTextChangedListener(this);
        editText_three.addTextChangedListener(this);
        editText_four.addTextChangedListener(this);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sendfab.setImageAssetsFolder("src/res/raw");
        sendfab.setAnimation("done.json");
        sendfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn;
                phn = phone.getText().toString();
                if (phn.isEmpty() || phn.length()<10){
                    Toast.makeText(LoginActivity.this, "Please Enter A Valid Number", Toast.LENGTH_SHORT).show();
                }else {
                    sendfab.playAnimation();
                    phonelayout.setVisibility(View.GONE);
                    otp_layout.setVisibility(View.VISIBLE);
                    sendVerificatioode();
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();
            }
        });
    }
    private void verifyOTP() {
        String e1 = editText_one.getText().toString();
        String e2 = editText_one.getText().toString();
        String e3 = editText_one.getText().toString();
        String e4 = editText_one.getText().toString();
        String code = e1+e2+e3+e4;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(LoginActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificatioode() {
        String phoneNumber = "+91"+phone.getText().toString();
            initFireBaseCallbacks();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks);
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    void initFireBaseCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(LoginActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(LoginActivity.this, "Code Sent", Toast.LENGTH_LONG).show();
                vCode = verificationId;
            }
        };
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1) {
            if (editText_one.length() == 1) {
                editText_two.requestFocus();
            }

            if (editText_two.length() == 1) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 1) {
                editText_four.requestFocus();
            }
            if (editText_four.length() == 1) {
                editText_five.requestFocus();
            }
            if (editText_five.length() == 1) {
                editText_six.requestFocus();
            }
        } else if (s.length() == 0) {
            if (editText_six.length()==0){
                editText_five.requestFocus();
            }
            if (editText_five.length() == 0) {
                editText_four.requestFocus();
            }
            if (editText_four.length() == 0) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 0) {
                editText_two.requestFocus();
            }
            if (editText_two.length() == 0) {
                editText_one.requestFocus();
            }
    }
}
}