package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GerarQRCodeTransferencia extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    ImageView ivQRCode;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_qrcode_transferencia);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ///TODO Receber texto Chave da outra Activity ou de o servidor

        iniciliarConponentes();


        txtView = (TextView) findViewById(R.id.textView47);
        txtView.setText(Global.receber+"$00");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null){
                    int money = userprofile.money;
                    Global.saldoQuemRecebe = money;
                    Global.userIDRecebe = userID;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String chave= userID+":"+Global.receber;
        randomChave(8);
        gerarQRCode(chave);

        //imageView53
    }
    private void gerarQRCode(String texto){

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix= multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,2000,2000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap= barcodeEncoder.createBitmap(bitMatrix);
            ivQRCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

    }
    public void irTelaAnterior (View View){
        Intent intent = new Intent(this, TelaOpcaoReceber.class);
        startActivity(intent);
    }

    public void irTelaInicialx (View View){
        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
    }
    private void iniciliarConponentes(){
        txtView = (TextView) findViewById(R.id.textView47);
        ivQRCode = (ImageView) findViewById(R.id.imageView53);

    }

    static String randomChave(int i) {
        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        //create the StringBuffer
        builder = new StringBuilder(i);

        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex
                    = (int)(theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }

        return builder.toString();
    }
}