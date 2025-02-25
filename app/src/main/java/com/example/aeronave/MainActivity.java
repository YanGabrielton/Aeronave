package com.example.aeronave;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView result;
    Button btSubir, btLigar, autorizarDecolar, autorizarPouso, checklist;
    ProgressBar progressBar;

    // Controle de estados
    boolean isLigado = false;         // para facilitar se está ligada
    boolean isSubindo = false;        //segue a mesma logica do isLigado
    boolean isChecklistFeito = false; // para verificar se o checklist foi feito
    boolean isAutorizado = false;    // para verificar se foi autorizado
    int voando=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        btSubir = findViewById(R.id.btSubir);
        btLigar = findViewById(R.id.btLigar);
        autorizarDecolar = findViewById(R.id.autorizarDecolar);
        autorizarPouso = findViewById(R.id.autorizarPouso);
        checklist = findViewById(R.id.checklist);
        progressBar = findViewById(R.id.progressBar);

        //Logica do botão de ligar e desligar em um botão ao inves de utilizar dois bottão separados
        btLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLigado) {
                    btLigar.setText("Ligar");
                    isLigado = false;
                    showToast("Aeronave desligada");
                } else {
                    btLigar.setText("Desligar");
                    isLigado = true;
                    showToast("Aeronave ligada");
                }

            }
        });
        //Checklist
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecklistFeito = true;
                showToast("Checklist concluído");
                checkAllConditions();
            }
        });

        //Autorização para decolar
        autorizarDecolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutorizado = true;
                showToast("Autorizado a decolar");

            }
        });

        //Autorização para pousar
        autorizarPouso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Autorizado Pouso", LENGTH_LONG).show();
            }
        });
        btSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("Decolando");

                if (voando <=40){
                 for (voando = 0; voando <=40; ){
                     result.setText(voando+10+"mil pés");
                 }

                } else if (voando == 40) {
                    result.setText(voando-40+"mil pés");
                }
            }
        });

    }


    private void checkAllConditions() {
        if (isLigado && isChecklistFeito && isAutorizado) {

            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#008000"), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(100);
            showToast("Aeronave pronta para decolar!");
        } else {

            progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(0); //
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
}
