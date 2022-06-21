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
        if(quantity >= 0 && quantity <= 20){
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Coin quantity should between 0 and 20");
        }
    }
}
