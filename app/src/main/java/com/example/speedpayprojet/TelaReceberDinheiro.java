package com.example.speedpayprojet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.Random;


public class TelaReceberDinheiro extends AppCompatActivity {
    EditText editText;
    DatabaseReference database;
    String chave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_enviar_dinheiro);
        iniciliarConponentes();

    }
    public void irTelaInicial (View View){
        Intent intent = new Intent(this, TelaInicial.class);
        startActivity(intent);
    }
    public void irTelaOpcaoReceber(View View){
        Intent intent = new Intent(this, TelaOpcaoReceber.class);

        //TODO e possivel tomar o valor logo como int


            String valor= editText.getText().toString();
        if (!valor.equals("")) {
            if (checkValue(Integer.parseInt(valor))) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID="Null";

                if (user != null) {
                    userID=user.getUid();
                } else {
                    // No user is signed in
                }
                //Todo como enviar INtente para outro activity
                Transasao transasao = new Transasao();
                transasao.setValor(valor);
                transasao.setIdPagar("Null");
                transasao.setDone(false);
                transasao.setIdReceber(userID);

                String chave = addTransasao(transasao);
                intent.putExtra("chavetrans",chave);
                intent.putExtra("valortrans",valor);
                startActivity(intent);
            } else {
                alert("valor invalido");
            }

        }else {
            alert("valor invalido");
        }
    }

    private String getChave() {
        //TODO tomar chave do servidor
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    private boolean checkValue(int valor) {
        ///TODO Ver se o valor e possivel
        if (valor>=50 && valor<=10000000) {
            return true;
        }else{
            return false;
        }
    }

    private void iniciliarConponentes() {
        editText = (EditText) findViewById(R.id.editTextNumber2);

    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public String addTransasao(Transasao transasao){
        database = FirebaseDatabase.getInstance().getReference("/Transasao");
        chave =database.push().getKey();
        database.child(chave).setValue(transasao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


            }
        });
        return chave;
    }
}