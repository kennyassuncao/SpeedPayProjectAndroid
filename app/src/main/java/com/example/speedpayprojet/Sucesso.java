package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sucesso extends AppCompatActivity {
    TextView val, ids,bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        val = (TextView) findViewById(R.id.val);
        val.setText(""+Global.valEnviar);
        ids = (TextView) findViewById(R.id.ids);
        ids.setText(Global.userIDRecebeScan);
        bb = (TextView) findViewById(R.id.textView73);
        bb.setText(""+Global.saldoQuemRecebeScan);

    }
    public void irTelaInicial (View View){
        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
    }
}