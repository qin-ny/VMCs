package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Machine {
    private transient Door door;
    private String password;
    private List<Slot> slots;
    private List<Coin> coins;
    private transient Set<Drink> drinks;



    public Machine(List<Slot> slots, List<Coin> coins, String password) {
        this.door = new Door(false);
        this.password = password;
        this.slots = slots;
        this.coins = coins;
        this.drinks = recordAllDrinks(slots);
    }

    /**
     * Check if the input a correct password.
     * @return boolean representing whether the password is correct
     */
    public boolean isValidPassword(String input){
        return password.equals(input);
    }

    public Set<Drink> recordAllDrinks(List<Slot> slots){
        Set<Drink> drinks = new HashSet<>();
        slots.forEach(slot -> drinks.add(slot.getDrink()));
//        System.out.println(drinks);
        return drinks;
    }

    /**
     * check if the drink name exist in the drinks,
     * @param name drink name
     * @return Option<Drink> means either the Drink was found or it is empty.
     */
    public Optional<Drink> getDrinkByName(String name){
        for(Drink drink: drinks){
            if (drink.getName().equals(name)) return Optional.of(drink);
        }
        return Optional.empty();
    }

    /**
     * check if the Coin name exist in the drinks,
     * @param name coin name
     * @return Option<Coin> means either the Coin was found or it is empty.
     */
    public Optional<Coin> getCoinByName(String name){
        for(Coin coin: coins){
            if (coin.getName().equals(name)) return Optional.of(coin);
        }
        return Optional.empty();
    }

    /**
     * calculates the total cash in the machine
     * @return the total cash in the machine
     */
    public int getTotalCash(){
        int totalCash = 0;
        for(Coin coin: coins){
            totalCash += coin.getTotalValue();
        }
        return totalCash;
    }

    /**
     * collect all coins from the machine
     * @return the total cash collected from the machine
     */
    public int collectAllCoins(){
        int totalCashCollected = getTotalCash();
        for(Coin coin: coins){
            coin.setQuantity(0);
        }
        return totalCashCollected;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public Set<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(Set<Drink> drinks) {
        this.drinks = drinks;
    }

}
