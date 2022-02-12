package com.example.speedpayprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class ConfirmarAutenticasao extends AppCompatActivity {
    Button btnConfirmar;
    Button btnCancelar;
    TextView txtvarlor;
    String chave;
    DatabaseReference database;

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    String userID;
    Transasao transasao;
    User userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_autenticasao);
        iniciliarConponentes();
        chave= getIntent().getStringExtra("chavetrans");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID="j48hvYWHMBS6kVDUIt8rSxPYeC82qq";
        if (user != null) {
            userID=user.getUid();
        } else {
            // No user is signed in
        }

        database = FirebaseDatabase.getInstance().getReference("/Transasao/"+chave);



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 transasao=snapshot.getValue(Transasao.class);
                 txtvarlor.setText(transasao.getValor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auten();

            }
        });

    }
    private void iniciliarConponentes() {
        btnCancelar = (Button) findViewById(R.id.button3);
        btnConfirmar= (Button) findViewById(R.id.button);
        txtvarlor = (TextView ) findViewById(R.id.valortextview);
    }
    public void irTelaSucesso (View View){
        Intent intent = new Intent(this, Sucesso.class);
        startActivity(intent);
    }
    public void auten() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "nao tem fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "no funciona", Toast.LENGTH_SHORT).show();
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "FingerPrint not Assingned", Toast.LENGTH_SHORT).show();
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(ConfirmarAutenticasao.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent= new Intent(ConfirmarAutenticasao.this,Sucesso.class);
                transasao.setIdPagar(userID);
                transasao.setDone(true);
                database = FirebaseDatabase.getInstance().getReference("");


                database.child("Transasao").child(chave).setValue(transasao).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                } );
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userInfo=snapshot.getValue(User.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
///
                    }
                });
                userInfo.setMoney(userInfo.getMoney()-Long.parseLong(transasao.getValor()));
                Toast.makeText(getApplicationContext(),""+userInfo.getMoney(),Toast.LENGTH_LONG).show();
                database.child("User").child(userID).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent= new Intent(ConfirmarAutenticasao.this,Sucesso.class);
                        startActivity(intent);

                    }
                } );

                startActivity(intent);



            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("Tech Projects").setDescription("useFinger").setDeviceCredentialAllowed(true).build();
        biometricPrompt.authenticate(promptInfo);



    }


}