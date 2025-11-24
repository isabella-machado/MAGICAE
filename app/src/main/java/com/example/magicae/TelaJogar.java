package com.example.magicae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaJogar extends AppCompatActivity {
    //declarar variavel
    private ImageView btnJogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_jogar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // inicia a view depois do setContentView ser chamado
        btnJogar = findViewById(R.id.btnJogar);

        //click listener no ImageView
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ir pra outra tela
                navigateToSelectionScreen();
            }
        });
    }

    private void navigateToSelectionScreen() {
        Intent intent = new Intent(TelaJogar.this, TelaSelecionar.class);
        startActivity(intent);
        finish(); // usu n pode voltar
    }
}