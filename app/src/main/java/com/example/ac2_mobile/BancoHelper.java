package com.example.ac2_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "FinancasDB";
    private static final int DB_VERSION = 1;

    public BancoHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gastos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT," +
                "valor REAL," +
                "categoria TEXT," +
                "data TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS gastos");
        onCreate(db);
    }

    public void inserirGasto(String descricao, double valor, String categoria, String data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao", descricao);
        valores.put("valor", valor);
        valores.put("categoria", categoria);
        valores.put("data", data);
        db.insert("gastos", null, valores);
    }

    public Cursor buscarGastos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM gastos", null);
    }
}