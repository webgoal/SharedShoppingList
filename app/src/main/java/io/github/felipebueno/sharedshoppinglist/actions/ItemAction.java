package io.github.felipebueno.sharedshoppinglist.actions;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import io.github.felipebueno.sharedshoppinglist.Item;

public abstract class ItemAction {
    protected String name;

    public ItemAction() { }

    public ItemAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected int itemFromList(ArrayList<Item> items) {
        return items.indexOf(new Item(name, false));
    }

    public String toJson() {
        try {
            return jsonMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ItemAction fromJson(String jsonString) {
        try {
            return jsonMapper().readValue(jsonString, ItemAction.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    private static ObjectMapper jsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_OBJECT);
        return objectMapper;
    }

    public abstract void run(ArrayList<Item> items);
}
