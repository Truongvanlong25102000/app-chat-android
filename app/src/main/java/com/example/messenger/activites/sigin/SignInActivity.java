package com.example.messenger.activites.sigin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messenger.R;
import com.example.messenger.activites.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private Button btnSignIn;
    private EditText edtUserName, edtPassWord, edtEmail, edt_pass_word_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        initEvent();
    }

    private void initEvent() {


        btnSignIn.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edt_pass_word_email.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    Toast.makeText(SignInActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        });
    }

    private void initView() {
        btnSignIn = findViewById(R.id.btn_sign_in);
        edtPassWord = findViewById(R.id.edt_pass_word);
        edtUserName = findViewById(R.id.edt_user_name);
        edtEmail = findViewById(R.id.edt_email);
        edt_pass_word_email = findViewById(R.id.edt_pass_word_email);
    }


}