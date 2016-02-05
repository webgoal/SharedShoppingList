package br.com.webgoal.sharedshoppinglist.actions;

import java.util.ArrayList;

import br.com.webgoal.sharedshoppinglist.Item;

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
