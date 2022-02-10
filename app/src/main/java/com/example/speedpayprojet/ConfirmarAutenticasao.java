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

import java.util.concurrent.Executor;

public class ConfirmarAutenticasao extends AppCompatActivity {
    Button btnConfirmar;
    Button btnCancelar;
    TextView txtvarlor;

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_autenticasao);
        iniciliarConponentes();
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