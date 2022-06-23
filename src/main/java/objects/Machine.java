package objects;

import observer.AuthorizationObservable;
import observer.CoinObservable;

import java.util.*;

public class Machine extends AuthorizationObservable {
    private final static String SGD = "c";

    private transient Door door;
    private String password;
    private List<Slot> slots;
    private List<Coin> coins;
    private transient Set<Drink> drinks;
    private transient boolean isAuthorized;

    public String getMoneyType() {
        return SGD;
    }

    public Machine(List<Slot> slots, List<Coin> coins, String password) {
        this.door = new Door(false);
        this.password = password;
        this.slots = slots;
        this.coins = coins;
        this.drinks = recordAllDrinks(slots);
        this.isAuthorized = false;
    }

    public boolean getAuthorization() {
        return this.isAuthorized;
    }

    public void setAuthorization(boolean status) {
        this.isAuthorized = status;
        setAuthorizationChanged();
        notifyAuthorizationObservers();
    }

    public int getCurrentEnteredMoney() {
        int currentTotalCash = 0;
        for (Coin coin: coins) {
            currentTotalCash += coin.getCurrentEnteredTotalValue();
        }
        return currentTotalCash;
    }

    public int collectCurrentEnteredCash() {
        int currentTotalCash = 0;
        for (Coin coin: coins) {
            currentTotalCash += coin.getCurrentEnteredTotalValue();
            coin.setCurrentEnteredQuantity(0);
        }
        return currentTotalCash;
    }

    public int collectALlCash() {
        int allCash = 0;
        for (Coin coin: coins) {
            allCash += coin.getTotalValue();
            coin.setQuantity(0);
            coin.setCurrentEnteredQuantity(0);
        }
        return allCash;
    }

    public void saveCurrentMoney() {
        for (Coin coin: coins) {
            coin.saveCurrentEnteredValue();
        }
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
     * Check if the input a correct password.
     * @return boolean representing whether the password is correct
     */
    public boolean isValidPassword(String input){
        return password.equals(input);
    }

    public Set<Drink> recordAllDrinks(List<Slot> slots){
        Set<Drink> drinks = new HashSet<>();
        slots.forEach(slot -> drinks.add(slot.getDrink()));
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

    public Optional<Slot> getSlotById(String id) {
        return getSlotById(Integer.parseInt(id));
    }

    public Optional<Slot> getSlotById(int id) {
        for(Slot slot: slots){
            if (slot.getId() == id) return Optional.of(slot);
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


    public Door getDoor() {
        return door;
    }

//    public void setDoor(Door door) {
//        this.door = door;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Slot> getSlots() {
        return slots;
    }

//    public void setSlots(List<Slot> slots) {
//        this.slots = slots;
//    }

    public List<Coin> getCoins() {
        return coins;
    }

//    public void setCoins(List<Coin> coins) {
//        this.coins = coins;
//    }

    public Set<Drink> getDrinks() {
        return drinks;
    }

//    public void setDrinks(Set<Drink> drinks) {
//        this.drinks = drinks;
//    }

}
