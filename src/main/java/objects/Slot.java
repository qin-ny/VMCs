package objects;

public class Slot {
    private Drink drink;
    private int quantity;


    public Slot(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    public Boolean isAvailable() {
        return quantity == 0;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
