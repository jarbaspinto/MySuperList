package com.example.mysuperlist.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysuperlist.R;
import com.example.mysuperlist.database.AppDatabase;
import com.example.mysuperlist.model.Item;
import com.example.mysuperlist.ui.recyclerview.adapter.listener.ItemItemClickListener;
import com.example.mysuperlist.ui.recyclerview.helper.ItemItemTouchHelper;

import java.util.Collections;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Item> itens;
    private ItemItemClickListener onItemClickListener;

    public ItemAdapter(Context context, List<Item> itens) {
        this.context = context;
        this.itens = itens;
    }

    public void setOnItemClickListener(ItemItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = itens.get(position);

        holder.vincula(item);

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void removeItemItem(int adapterPosition){
        AppDatabase
                .getInstance(context).itemDao().delete(itens.get(adapterPosition));
        itens.remove(adapterPosition);
        notifyDataSetChanged();
    }

    public void alteraPosicao(int posicaoInicial, int posicaoFinal){
        Collections.swap(itens, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial,posicaoFinal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textItemNome;
        private TextView textQuantidade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textItemNome = itemView.findViewById(R.id.itemNome);
            textQuantidade = itemView.findViewById(R.id.itemQuantidade);
        }
        private void vincula(Item item){
            textItemNome.setText(item.getNomeDoItem());
            textQuantidade.setText(item.getQuantidadeDoItemTexto());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicao = getAdapterPosition();
                    Item item = itens.get(getAdapterPosition());
                    onItemClickListener.itemClick(item, posicao);
                }
            });
        }
    }
}
