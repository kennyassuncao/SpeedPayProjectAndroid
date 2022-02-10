package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InserirChaveTransferencia extends AppCompatActivity {
    ImageView btnConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_chave_transferencia);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnConfirmar= (ImageView) findViewById(R.id.imageView109) ;
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(InserirChaveTransferencia.this,ConfirmarAutenticasao.class);
                startActivity(intent);

            }
        });
    }
    public void irTelaAnterior (View View){
        Intent intent = new Intent(this, TelaEnviarDinheiroSpeed.class);
        startActivity(intent);
    }
    public void irTelaAutenticasao (View View){
        Intent intent = new Intent(this,ConfirmarAutenticasao.class);
        startActivity(intent);
    }
}