package com.example.ac2_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroActivity extends AppCompatActivity {

    EditText editDescricao, editValor;
    Spinner spinnerCategoria;
    Button buttonSalvar;
    BancoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        helper = new BancoHelper(this);

        String[] categorias = {"Alimentação", "Transporte", "Lazer", "Investimento", "Outros"};
        spinnerCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias));

        buttonSalvar.setOnClickListener(v -> {
            String descricao = editDescricao.getText().toString();
            double valor = Double.parseDouble(editValor.getText().toString());
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            helper.inserirGasto(descricao, valor, categoria, data);
            finish();
        });
    }
}