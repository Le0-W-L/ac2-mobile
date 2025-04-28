package com.example.ac2_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class ResumoActivity extends AppCompatActivity {

    TextView textResumo;
    BancoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        textResumo = findViewById(R.id.textResumo);
        helper = new BancoHelper(this);

        calcularResumo();
    }

    private void calcularResumo() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            Cursor c = helper.buscarGastos();
            Map<String, Double> totalPorCategoria = new HashMap<>();
            double totalGeral = 0;

            while (c.moveToNext()) {
                String categoria = c.getString(3);
                double valor = c.getDouble(2);

                totalGeral += valor;
                double atual = totalPorCategoria.getOrDefault(categoria, 0.0);
                totalPorCategoria.put(categoria, atual + valor);
            }

            StringBuilder resumo = new StringBuilder();
            String maiorCategoria = "";
            double maiorValor = 0;

            for (Map.Entry<String, Double> entry : totalPorCategoria.entrySet()) {
                resumo.append(entry.getKey()).append(": R$ ").append(entry.getValue()).append("\n");

                if (entry.getValue() > maiorValor) {
                    maiorValor = entry.getValue();
                    maiorCategoria = entry.getKey();
                }
            }

            resumo.append("\nTotal gasto: R$ ").append(totalGeral);
            resumo.append("\nCategoria com maior gasto: ").append(maiorCategoria);

            runOnUiThread(() -> textResumo.setText(resumo.toString()));
        }).start();
    }
}