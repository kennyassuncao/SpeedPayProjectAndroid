package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GerarQRCodeTransferencia extends AppCompatActivity {
    ImageView ivQRCode;
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_qrcode_transferencia);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        iniciliarConponentes();
        String chave=getIntent().getStringExtra("chavetrans");
        String valor=getIntent().getStringExtra("valortrans");

        txtView.setText(valor+"$00");

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