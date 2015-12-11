package io.github.felipebueno.sharedshoppinglist.actions;

import java.util.ArrayList;

import io.github.felipebueno.sharedshoppinglist.Item;

public class ItemUncheckAction extends ItemAction {

    public ItemUncheckAction() { }

    public ItemUncheckAction(String name) {
        super(name);
    }

    public void run(ArrayList<Item> items) {
        items.get(itemFromList(items)).isDone = false;
    }

}