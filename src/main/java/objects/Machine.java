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
    private transient boolean isAuthorized;

    public String getMoneyType() {
        return SGD;
    }

    public Machine(List<Slot> slots, List<Coin> coins, String password) {
        this.door = new Door(false);
        this.password = password;
        this.slots = slots;
        this.coins = coins;
        this.isAuthorized = false;
    }

    public boolean getAuthorization() {
        return this.isAuthorized;
    }

    public void setAuthorization(boolean status) {
        this.isAuthorized = status;
        setChanged();
        notifyObservers();
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
            int value = coin.getCurrentEnteredTotalValue();
            if (value == 0) continue;
            currentTotalCash += value;
            coin.setCurrentEnteredQuantity(0);
        }
        return currentTotalCash;
    }

    public int collectAllCash() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public List<Coin> getCoins() {
        return coins;
    }

}
