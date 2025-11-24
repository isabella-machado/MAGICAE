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

public class TelaSelecionar extends AppCompatActivity {

    private ImageView ivJogoMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_selecionar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // inicia a view depois do setContentView ser chamado
        ivJogoMemoria = findViewById(R.id.ivJogoMemoria);

        //click listener no ImageView
        ivJogoMemoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ir pra outra tela
                navigateToGameScreen();
            }
        });
    }

    private void navigateToGameScreen() {
        Intent intent = new Intent(TelaSelecionar.this, TelaJogoMemoria.class);
        startActivity(intent);
        finish(); // usu n pode voltar
    }
}