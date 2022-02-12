package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class TelaRegistrar extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private EditText editTextFullname, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registrar);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullname = (EditText) findViewById(R.id.fullname);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullname.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        int money = 0;

        if (fullname.isEmpty()) {
            editTextFullname.setError("Deve Introduzir o nome completo!");
            editTextFullname.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            editTextAge.setError("Deve Introduzir a Idade!");
            editTextAge.requestFocus();
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Deve Introduzir o Email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Deve Introduzir um Email válido");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Deve Introduzir a senha!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            editTextPassword.setError("A senha deve conter pelomenos 8 caracteres");
            editTextPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullname, age, email, money);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(TelaRegistrar.this, "Registrado com Sucesso", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to login layout
                                    } else {
                                        Toast.makeText(TelaRegistrar.this, "Falha Registo! Tente Novamente!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(TelaRegistrar.this, "Falha Registo! Tente Novamente!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void irTelaInicial(View View) {
        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                registerUser();
                break;

        }
    }
}
