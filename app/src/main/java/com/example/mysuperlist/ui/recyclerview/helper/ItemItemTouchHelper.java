package com.example.mysuperlist.ui.recyclerview.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysuperlist.model.Item;
import com.example.mysuperlist.ui.recyclerview.adapter.ItemAdapter;

public class ItemItemTouchHelper extends ItemTouchHelper.Callback {

    private ItemAdapter adapter;

    public ItemItemTouchHelper(ItemAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentoSwipe = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentoDrag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(movimentoDrag, movimentoSwipe);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();

        adapter.alteraPosicao(posicaoInicial, posicaoFinal);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    adapter.removeItemItem(viewHolder.getAdapterPosition());
    }
}
