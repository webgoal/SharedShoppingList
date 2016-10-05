package br.com.webgoal.sharedshoppinglist.actions;

import java.util.ArrayList;

import br.com.webgoal.sharedshoppinglist.Item;

public class ItemRemoveAction extends ItemAction {

    public ItemRemoveAction() { }

    public ItemRemoveAction(String name) {
        super(name);
    }

    public void run(ArrayList<Item> items) {
        int index = itemFromList(items);
        if (index > 0) items.remove(index);
    }
}