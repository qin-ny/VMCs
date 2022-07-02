package objects;

import observer.Observable;

public class Slot extends Observable {

    public enum SlotObserverType {
        PRICE,
        NAME,
        QUANTITY
    }

    private int id;
    private Drink drink;
    private int quantity;


    public Slot(int id, Drink drink, int quantity) {
        this.id = id;
        this.drink = drink;
        this.quantity = quantity;
    }

    public Boolean isAvailable() {
        return quantity == 0;
    }

    public int getId() {
        return id;
    }

    public Drink getDrink() {
        return this.drink;
    }

    public String getName() {
        return drink.getName();
    }

    public void setName(String name) {
        drink.setName(name);
        setChanged();
        notifyObservers(SlotObserverType.NAME);
    }

    public int getPrice() { return drink.getPrice(); }

    public void setPrice(int price) {
        drink.setPrice(price);
        setChanged();
        notifyObservers(SlotObserverType.PRICE);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setChanged();
        notifyObservers(SlotObserverType.QUANTITY);
    }
}
