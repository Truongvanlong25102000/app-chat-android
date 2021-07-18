package com.example.messenger.activites.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messenger.R;
import com.example.messenger.activites.MainActivity;
import com.example.messenger.activites.sigin.SignInActivity;
import com.example.messenger.helpers.databases.FireBaseController;
import com.example.messenger.helpers.databases.FireBaseTableKey;
import com.example.messenger.models.AccountResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnLogin;
    private EditText edtUserName, edtPassWord;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        initView();
        initEvent();
    }

    private void initEvent() {
        btnLogin.setOnClickListener(v -> {
            String userName = edtUserName.getText().toString().trim();
            String passWord = edtPassWord.getText().toString().trim();

//            FireBaseController.getInstance().getData(FireBaseTableKey.ACCOUNT_KEY + userName, new FireBaseController.RetrieveCallBack() {
//                @Override
//                public void retrieveSuccess(DataSnapshot data) {
//                    if (data.exists()) {
//                        AccountResponse accountResponse = data.getValue(AccountResponse.class);
//                        if (accountResponse.getPassword().equals(passWord)) {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Sai MK", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void retrieveFail(DatabaseError error) {
//                    Toast.makeText(LoginActivity.this, "Sai TK or MK", Toast.LENGTH_SHORT).show();
//                }
//            });

            firebaseAuth.signInWithEmailAndPassword(userName,passWord).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    } else {
                        String userName = edtUserName.getText().toString().trim().replace(".","_");
                        FirebaseDatabase.getInstance().getReference("Account").child(userName).child("password").setValue(passWord);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        });

        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignInActivity.class));
        });
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignIn = findViewById(R.id.btn_sign_in);
        edtPassWord = findViewById(R.id.edt_pass_word);
        edtUserName = findViewById(R.id.edt_user_name);
    }
}