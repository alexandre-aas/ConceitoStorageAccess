package com.s.d.a.a.conceitostorageaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ConceitoStorageAccessActivity extends AppCompatActivity {

    private static EditText edtTexto;

    private static final int CODIGO_CRIAR_ARQUIVO  = 40;
    private static final int CODIGO_ABRIR_ARQUIVO  = 41;
    private static final int CODIGO_SALVAR_ARQUIVO = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceito_storage_access);

        edtTexto = findViewById(R.id.pltTexto);
    }

    public void novoArquivo(View view){
        //Definir ação com uma Intent específica
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "arquivo_texto.txt");

        startActivityForResult(intent, CODIGO_CRIAR_ARQUIVO);

    }

    public void onActivityResult(int codigo_Operacao, int codigo_Resultado, Intent dados){

        if (codigo_Resultado == Activity.RESULT_OK) {

            if (codigo_Operacao == CODIGO_CRIAR_ARQUIVO) {
                if (dados != null) {
                    edtTexto.setText("");
                }
            }
        }

    }
}
