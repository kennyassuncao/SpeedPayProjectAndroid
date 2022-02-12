package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class GerarQRCodeTransferencia extends AppCompatActivity {
    ImageView ivQRCode;
    TextView txtView;
    DatabaseReference database;
    DatabaseReference mdatabase;
    String chave;
    String valor;

    String userID;
    Long money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_qrcode_transferencia);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        iniciliarConponentes();
        chave=getIntent().getStringExtra("chavetrans");
        valor=getIntent().getStringExtra("valortrans");

        txtView.setText(valor+"$00");

        gerarQRCode(chave);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID="j48hvYWHMBS6kVDUIt8rSxPYeC82qq";
        if (user != null) {
            userID=user.getUid();
        } else {
            // No user is signed in
        }
        database = FirebaseDatabase.getInstance().getReference("/Users/"+userID+"/money");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                money=(Long) snapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database = FirebaseDatabase.getInstance().getReference("/Transasao/"+chave);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((boolean)snapshot.child("done").getValue()){
                    Intent intent = new Intent(GerarQRCodeTransferencia.this, Sucesso.class);

                    //Toast.makeText(getApplicationContext(),"deu ",Toast.LENGTH_LONG).show();
                    //money= (Long) snapshot.child("User").child(userID).child("money").getValue();

                    Toast.makeText(getApplicationContext(),"deu "+money,Toast.LENGTH_LONG).show();

                    HashMap hashMap =new HashMap();
                    hashMap.put("money", money+Long.parseLong(valor));



                    mdatabase = FirebaseDatabase.getInstance().getReference("/Users/"+userID);
                    mdatabase.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                        }
                    });
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        String chave= getIntent().getStringExtra("chavetrans");
        String valor= getIntent().getStringExtra("valortrans");
        intent.putExtra("chavetrans",chave);
        intent.putExtra("valortrans",valor);
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
}