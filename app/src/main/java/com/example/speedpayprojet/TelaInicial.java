package com.example.speedpayprojet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaInicial extends AppCompatActivity {

    DatabaseReference database;

    TextView tvusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID="j48hvYWHMBS6kVDUIt8rSxPYeC82qq";
        if (user != null) {
            userID=user.getUid();
        } else {
            // No user is signed in
        }

        database = FirebaseDatabase.getInstance().getReference("/Users/"+userID+"/fullname");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvusername=(TextView) findViewById(R.id.textView20);
                tvusername.setText((String)snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
    public void irTelaCarteira (View View){
        Intent intent = new Intent(this, TelaCarteira.class);
        startActivity(intent);
    }
    public void irTelaHistorico (View View){
        Intent intent = new Intent(this, TelaHistorico.class);
        startActivity(intent);
    }

    public void irTelaReceberDinheiro (View View){
        Intent intent = new Intent(this, TelaReceberDinheiro.class);
        startActivity(intent);
    }

    public void irTelaEnviarDinheiro(View View){
        Intent intent = new Intent(this, TelaEnviarDinheiroSpeed.class);
        startActivity(intent);
    }

    public void irTelaConfiguracoes(View View){
        Intent intent = new Intent(this, TelaConfiguracoes.class);
        startActivity(intent);
    }

}