package io.github.felipebueno.sharedshoppinglist.actions;

import java.util.ArrayList;

import io.github.felipebueno.sharedshoppinglist.Item;

public class ItemRemoveAction extends ItemAction {

    public ItemRemoveAction() { }

    public ItemRemoveAction(String name) {
        super(name);
    }

    public void run(ArrayList<Item> items) {
        items.remove(itemFromList(items));
    }
}