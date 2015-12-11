package io.github.felipebueno.sharedshoppinglist;

import org.junit.Test;

import io.github.felipebueno.sharedshoppinglist.actions.ItemAction;
import io.github.felipebueno.sharedshoppinglist.actions.ItemAddAction;

import static org.junit.Assert.*;

public class JsonSerializationUnitTest {

	@Test public void isOk() throws Exception {
		String name = "Altz";
		ItemAction createdAction = new ItemAddAction(name);
		String serializedString = createdAction.toJson();
		ItemAction deserializedAction = ItemAction.fromJson(serializedString);

		assertEquals(name, deserializedAction.getName());
	}

}
