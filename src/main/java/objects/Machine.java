package objects;

import java.util.*;

public class Machine {
    private final static String SGD = "c";
    private transient Door door;
    private String password;
    private List<Slot> slots;
    private List<Coin> coins;


    public Machine(List<Slot> slots, List<Coin> coins, String password) {
        this.door = new Door(false);
        this.password = password;
        this.slots = slots;
        this.coins = coins;
    }

    public String getMoneyType() {
        return SGD;
    }

    public int getCurrentEnteredMoney() {
        int currentTotalCash = 0;
        for (Coin coin: coins) {
            currentTotalCash += coin.getCurrentEnteredTotalValue();
        }
        return currentTotalCash;
    }

    public int collectALlCash() {
        int allCash = getTotalCash();
        for (Coin coin: coins) {
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

    public Optional<Slot> getSlotByIndex(int index) {
        if(index < slots.size()){
            Slot slot = slots.get(index);
            if (slot != null) return Optional.of(slot);
            else return Optional.empty();
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

}
