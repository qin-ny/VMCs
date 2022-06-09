package objects;

import java.util.ArrayList;
import java.util.List;

public class Machine {
    private Door door;
    private List<Drink> drinks;
    private List<Coin> coins;


    public Machine(Door door, List<Drink> drinks, List<Coin> coins) {
        this.door = door;
        this.drinks = drinks;
        this.coins = coins;
    }


    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

}
