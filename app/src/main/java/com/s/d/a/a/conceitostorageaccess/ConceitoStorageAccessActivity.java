package com.s.d.a.a.conceitostorageaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.os.ParcelFileDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public void salvarArquivo(View view)
    {
        //A Intent chamará o Picker User Interface e filtrará a lista de arquivo p/ somente texto
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, CODIGO_SALVAR_ARQUIVO);
    }

    public void abrirArquivo(View view)
    {
        //Poderia ter sido definido apenas um método p/ executar as funções de salvar e abrir um
        //documento de texto, visto que o código é exatamente o mesmo, salvo a exceção do código
        //de operação.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, CODIGO_ABRIR_ARQUIVO);
    }

    public void onActivityResult(int codigo_Operacao, int codigo_Resultado, Intent dados){
        Uri atualUri = null;

        if (codigo_Resultado == Activity.RESULT_OK) {

            if (codigo_Operacao == CODIGO_CRIAR_ARQUIVO) {
                if (dados != null) {
                    edtTexto.setText("");
                }
            } else if (codigo_Operacao == CODIGO_SALVAR_ARQUIVO) {
                if (dados != null) {
                    atualUri = dados.getData();
                    gravarConteudoArquivo(atualUri);
                }
            } else if(codigo_Operacao == CODIGO_ABRIR_ARQUIVO){
                if (dados != null) {
                    atualUri = dados.getData();
                    try {
                        String conteudoDoArquivo = lerConteudoArquivo(atualUri);
                        edtTexto.setText(conteudoDoArquivo);
                    } catch (IOException e) {
                        //tratar eventuais erros aqui
                    }
                }
            }
        }

    }

    private void gravarConteudoArquivo(Uri uri)
    {
        try{
            ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());

            String textoASerGravado = edtTexto.getText().toString();

            fileOutputStream.write(textoASerGravado.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lerConteudoArquivo(Uri uri) throws IOException{
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader   = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String linhaAtual;

        while ((linhaAtual = reader.readLine()) != null) {
            stringBuilder.append(linhaAtual + "\n");
        }
        inputStream.close();
        reader.close();

        return stringBuilder.toString();

    }

}
