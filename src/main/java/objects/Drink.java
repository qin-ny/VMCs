package objects;

import observer.SlotObservable;

public class Drink {
    private String name;
    private int price; //in Singapore cents
    private final static String  type = "c";


    public Drink(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }
}
