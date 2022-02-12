package com.example.speedpayprojet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class Scan_QRCode extends AppCompatActivity {
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        final Activity activity= this;

        IntentIntegrator integrator=new IntentIntegrator(activity);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);


        integrator.setPrompt("Camera Scan");

        integrator.setCameraId(0);

        integrator.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){
                alert(result.getContents());
                if(chaveNoServidor(result.getContents())){
                    String chave=result.getContents();
                    database = FirebaseDatabase.getInstance().getReference("/Transasao/"+chave);



                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            irTelaAutenticasao(chave);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    ///TODO trocar para "irTelaConfirmar" e enviar o valor como intent

                }


            }else{
                alert("Scan cancelado");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private int getValorDoServidor(String contents) {
        //TODO
        return 501;
    }

    private boolean chaveNoServidor(String contents) {
        //TODO
        return true;
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void irTelaAutenticasao (String chave){

        Intent intent = new Intent(this,ConfirmarAutenticasao.class);
        intent.putExtra("chavetrans",chave);
        startActivity(intent);
    }
}
