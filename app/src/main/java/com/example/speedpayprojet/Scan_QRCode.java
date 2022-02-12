package com.example.speedpayprojet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;


public class Scan_QRCode extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        final Activity activity= this;

        IntentIntegrator integrator=new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Camera Scan");
        integrator.setCameraId(0);
        integrator.initiateScan();



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    int money = userProfile.money;
                    Global.saldoQuemEnvia = money;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){
                alert(result.getContents());
                /*if(chaveNoServidor(result.getContents())){
                    int valor=getValorDoServidor(result.getContents());
                    ///TODO trocar para "irTelaConfirmar" e enviar o valor como intent
                    irTelaSucesso();
                }*/
                Global.scanValue = result.getContents();

                String separar = Global.scanValue;

                String[] splitted= separar.split(":");
                String splitted1= splitted[0]; //ID gerador do QR CODE
                String splitted2 = splitted[1]; //Valor a receber do gerador do QR CODE em string
                int valenviar = Integer.parseInt(splitted2);    //Valor a receber do gerador do QR CODE em inteiro
                Global.userIDRecebeScan = splitted1;        //ID de quem recebe passado no qr code
                Global.valEnviar = valenviar;               // Valor a ser enviado passado no QR code

                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users");
                userID = user.getUid();

                reference.child(Global.userIDRecebeScan).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userprofile3 = snapshot.getValue(User.class);
                        if(userprofile3 != null){
                        Global.saldoQuemRecebeScan = userprofile3.money;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                int saldoAoReceber = Global.saldoQuemRecebeScan+Global.valEnviar;
               // int val = Integer.parseInt(input.getTex)
                HashMap hashMap = new HashMap();
                hashMap.put("money", saldoAoReceber);

                reference.child(Global.userIDRecebeScan).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Scan_QRCode.this, "Sucesso", Toast.LENGTH_SHORT).show();
                    }
                });
                irTelaSucesso();

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
    public void irTelaSucesso (){
        Intent intent = new Intent(this, Sucesso.class);
        startActivity(intent);
    }
}
