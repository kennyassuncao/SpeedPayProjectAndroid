package com.example.speedpayprojet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class TelaReceberDinheiro extends AppCompatActivity {
    EditText editText;
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
                //Todo como enviar INtente para outro activity
                String chave = getChave();
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
        return "FxsG365Gx";
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
}