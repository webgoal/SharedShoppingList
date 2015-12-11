package io.github.felipebueno.sharedshoppinglist.actions;

import java.util.ArrayList;

import io.github.felipebueno.sharedshoppinglist.Item;

public class ItemAddAction extends ItemAction {

    public ItemAddAction() { }

    public ItemAddAction(String name) {
        super(name);
    }

    public void run(ArrayList<Item> items) {
        if (itemFromList(items) == -1)
            items.add(new Item(name, false));
    }
}
