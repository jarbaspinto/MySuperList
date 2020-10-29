package com.example.mysuperlist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mysuperlist.R;
import com.example.mysuperlist.model.Item;

public class FormItemActivity extends AppCompatActivity {

    private EditText editNomeProduto;
    private EditText editQuantidadeProduto;
    private Button buttonAdicionaProduto;
    private Boolean eFormularioEdicao;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_item);

        carregaCampos();
        cliqueBotao();

        Intent intent = getIntent();
        if (intent.hasExtra(Constantes.CHAVE_EDICAO_ITEM)){
            eFormularioEdicao = true;
            item = (Item) intent.getSerializableExtra(Constantes.CHAVE_EDICAO_ITEM);
            carregaDadosFormulario();
        }
    }

    private void carregaDadosFormulario(){
        editNomeProduto.setText(item.getNomeDoItem());
        editQuantidadeProduto.setText(item.getQuantidadeDoItem());
    }

    private void cliqueBotao(){
        buttonAdicionaProduto = findViewById(R.id.buttonAdicionaProduto);
        buttonAdicionaProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eFormularioEdicao){
                    atualizaItem();

                    Intent intent = new Intent(FormItemActivity.this,MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_EDICAO_ITEM, item);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    item = pegaItemDoFormulario();

                    Intent intent = new Intent(FormItemActivity.this, MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_NOVO_ITEM, item);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void atualizaItem(){
        String nomeItem = editNomeProduto.getText().toString();
        int quantidadeItem = Integer.valueOf(editQuantidadeProduto.getText().toString());

        item.setNomeDoItem(nomeItem);
        item.setQuantidadeDoItem(quantidadeItem);
    }

    private Item pegaItemDoFormulario(){
        String nomeItem = editNomeProduto.getText().toString();
        int quantidadeItem = Integer.valueOf(editQuantidadeProduto.getText().toString());

        return new Item(nomeItem, quantidadeItem);
    }

    private void carregaCampos(){
        editNomeProduto = findViewById(R.id.editTextNomeProduto);
        editQuantidadeProduto = findViewById(R.id.editTextQuantidadeProduto);
    }
}