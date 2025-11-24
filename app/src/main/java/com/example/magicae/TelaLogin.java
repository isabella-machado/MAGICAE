package com.example.magicae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaLogin extends AppCompatActivity {

    private EditText edtEmailLog;
    private EditText pSenhaLog;
    private Button btnLoginUsnUsu;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        edtEmailLog = findViewById(R.id.edtEmailLog);
        pSenhaLog = findViewById(R.id.pSenhaLog);
        btnLoginUsnUsu = findViewById(R.id.btnLoginUsnUsu);


        btnLoginUsnUsu.setOnClickListener(v -> {
            String email = edtEmailLog.getText().toString();
            String senha = pSenhaLog.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha o email e a senha", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.validarLogin(email, senha)) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TelaLogin.this, TelaJogar.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
