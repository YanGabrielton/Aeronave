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
    boolean isSubindo = false;        // define se está subindo ou descendo
    boolean isChecklistFeito = false; // para verificar se o checklist foi feito
    boolean isAutorizado = false;    // para verificar se foi autorizado
    boolean isAutorizadoDescer = false;    // para verificar se foi autorizado
    int voando = 0;                   // altura atual

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

        // Lógica do botão de ligar/desligar
        btLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLigado) {
                    btLigar.setText("Ligar");
                    isLigado = false;
                    showToast("Aeronave desligada");
                    checkAllConditions();
                } else {
                    btLigar.setText("Desligar");
                    isLigado = true;
                    showToast("Aeronave ligada");
                    checkAllConditions();
                }
            }
        });

        // Checklist
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecklistFeito = true;
                showToast("Checklist concluído");
                checkAllConditions();
            }
        });

        // Autorização para decolar
        autorizarDecolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutorizado = true;
                showToast("Autorizado a decolar");
                checkAllConditions();
            }
        });

        // Autorização para pousar
        autorizarPouso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutorizadoDescer = true;
                Toast.makeText(getApplicationContext(), "Autorizado Pouso", LENGTH_LONG).show();
            }
        });

        // Lógica do botão de subida e descida
        btSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se a aeronave está ligada, checklist feito e autorizado
                if (isLigado && isChecklistFeito && isAutorizado) {
                    // Se está subindo, aumente a altura
                    if (!isSubindo) {
                        isSubindo = true; // começa a subir
                        result.setText("Decolando");
                        voando = 40; // Subindo diretamente para 40 mil pés
                        result.setText(voando + " mil pés");

                    }
                    // Se já está subindo, faz a descida
                    else {
                        isSubindo = false; // começa a descer
                        result.setText("Descendo");
                        voando = 0; // Desce diretamente para 0 pés
                        result.setText(voando + " Você Pousou");
                    }
                } else {
                    showToast("Não é possível decolar ou pousar. Verifique as condições.");
                }
            }
        });
    }

    // Método para verificar as condições para a aeronave estar pronta
    private void checkAllConditions() {
        if (isLigado && isChecklistFeito && isAutorizado) {
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#008000"), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(100);
            showToast("Aeronave pronta para decolar!");
        } else {
            progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgress(0);
        }
    }

    // Método para mostrar mensagens de Toast
    private void showToast(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
}
