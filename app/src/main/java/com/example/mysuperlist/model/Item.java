package com.example.mysuperlist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Item")
    private String nomeDoItem;
    @ColumnInfo(name = "Quantidade")
    private int quantidadeDoItem;

    public Item(String nomeDoItem, int quantidadeDoItem) {
        this.nomeDoItem = nomeDoItem;
        this.quantidadeDoItem = quantidadeDoItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDoItem() {
        return nomeDoItem;
    }

    public void setNomeDoItem(String nomeDoItem) {
        this.nomeDoItem = nomeDoItem;
    }

    public int getQuantidadeDoItem() {
        return quantidadeDoItem;
    }
    public String getQuantidadeDoItemTexto(){
        return String.valueOf(quantidadeDoItem);
    }

    public void setQuantidadeDoItem(int quantidadeDoItem) {
        this.quantidadeDoItem = quantidadeDoItem;
    }
}
