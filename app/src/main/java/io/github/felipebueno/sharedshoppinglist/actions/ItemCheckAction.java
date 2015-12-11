package io.github.felipebueno.sharedshoppinglist.actions;

import java.util.ArrayList;

import io.github.felipebueno.sharedshoppinglist.Item;

public class ItemCheckAction extends ItemAction {

    public ItemCheckAction() { }

    public ItemCheckAction(String name) {
        super(name);
    }

    public void run(ArrayList<Item> items) {
        items.get(itemFromList(items)).isDone = true;
    }

}
