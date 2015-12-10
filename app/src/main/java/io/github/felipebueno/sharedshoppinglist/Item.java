package io.github.felipebueno.sharedshoppinglist;

public class Item {

	public String name;
	public boolean isDone;

	public Item(String name, boolean isDone) {
		this.name = name;
		this.isDone = isDone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Item item = (Item) o;

		return name.equals(item.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
