package com.example.mysuperlist.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mysuperlist.R;
import com.example.mysuperlist.database.AppDatabase;
import com.example.mysuperlist.model.Item;
import com.example.mysuperlist.ui.recyclerview.adapter.ItemAdapter;
import com.example.mysuperlist.ui.recyclerview.adapter.listener.ItemItemClickListener;
import com.example.mysuperlist.ui.recyclerview.helper.ItemItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMainItens;
    private FloatingActionButton fabAdicionaItem;

    static List<Item> itens;
    private ItemAdapter adapter;

    private int posicaoItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geraItemLista();
        configuraRecyclerView();
        cliquebotao();
    }

    private void cliquebotao(){
        fabAdicionaItem = findViewById(R.id.fabNovoItem);

        fabAdicionaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FormItemActivity.class);

                startActivityForResult(intent, Constantes.SOLICITA_NOVO_ITEM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constantes.SOLICITA_NOVO_ITEM && data.hasExtra(Constantes.CHAVE_NOVO_ITEM)){
            if (resultCode == Activity.RESULT_OK){
                Item item = (Item) data.getSerializableExtra(Constantes.CHAVE_NOVO_ITEM);

                AppDatabase
                        .getInstance(getApplicationContext())
                        .itemDao()
                        .insert(item);

                itens.clear();
                itens.addAll(AppDatabase
                        .getInstance(getApplicationContext())
                        .itemDao()
                        .getAll());
                adapter.notifyDataSetChanged();

            }
        }

        if (requestCode == Constantes.SOLICITA_EDICAO_ITEM && data.hasExtra(Constantes.CHAVE_EDICAO_ITEM)){
            if (resultCode == Activity.RESULT_OK){
                Item item = (Item) data.getSerializableExtra(Constantes.CHAVE_EDICAO_ITEM);
                AppDatabase
                        .getInstance(getApplicationContext())
                        .itemDao().update(item);
                itens.set(posicaoItemClick, item);
                adapter.notifyItemChanged(posicaoItemClick);
            }
        }
    }

    private void configuraRecyclerView(){
        recyclerViewMainItens = findViewById(R.id.recyclerViewItens);

        recyclerViewMainItens.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(getApplicationContext(), itens);
        recyclerViewMainItens.setAdapter(adapter);
        adapter.setOnClickListener(new ItemItemClickListener() {
            @Override
            public void itemClick(Item item, int posicao) {
                posicaoItemClick = posicao;
                Intent intent = new Intent(MainActivity.this,FormItemActivity.class);
                intent.putExtra(Constantes.CHAVE_EDICAO_ITEM, item);
                startActivityForResult(intent,Constantes.SOLICITA_EDICAO_ITEM);
                Log.i("teste", "teste");
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerViewMainItens);
    }

    void geraItemLista(){
        itens = AppDatabase.getInstance(getApplicationContext())
                .itemDao().getAll();
    }
}