package com.example.ac2_mobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewGastos;
    Button buttonAdd, buttonResumo;
    BancoHelper helper;
    ArrayList<String> listaGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listViewGastos = findViewById(R.id.listView);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonResumo = findViewById(R.id.buttonResumo);
        helper = new BancoHelper(this);

        buttonAdd.setOnClickListener(v -> startActivity(new Intent(this, CadastroActivity.class)));

        buttonResumo.setOnClickListener(v -> startActivity(new Intent(this, ResumoActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarGastos();
    }

    private void carregarGastos() {
        listaGastos = new ArrayList<>();
        Cursor c = helper.buscarGastos();
        while (c.moveToNext()) {
            String desc = c.getString(1);
            double valor = c.getDouble(2);
            String cat = c.getString(3);
            String data = c.getString(4);

            listaGastos.add(desc + "\nR$ " + valor + "\nCategoria: " + cat + "\nData: " + data);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaGastos);
        listViewGastos.setAdapter(adapter);
    }
}