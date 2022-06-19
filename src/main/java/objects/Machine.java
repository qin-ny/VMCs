package objects;

import java.util.*;

public class Machine {
    private final static String SGD = "c";

    private transient Door door;
    private String password;
    private List<Slot> slots;
    private List<Coin> coins;
    private transient Set<Drink> drinks;
//    private Map<String, Coin> currentEnteredCoins;

    public String getMoneyType() {
        return SGD;
    }

    public Machine(List<Slot> slots, List<Coin> coins, String password) {
        this.door = new Door(false);
        this.password = password;
        this.slots = slots;
        this.coins = coins;
        this.drinks = recordAllDrinks(slots);
//        this.currentEnteredCoins = new HashMap<>();
    }

    public int getCurrentEnteredMoney() {
        int currentTotalCash = 0;
        for (Coin coin: coins) {
            currentTotalCash += coin.getCurrentEnteredTotalValue();
        }
//
//        int currentTotalCash = 0;
//        for (Coin coin: currentEnteredCoins.values()) {
//            currentTotalCash += coin.getWeight() * coin.getQuantity();
//        }
        return currentTotalCash;
    }

//    public Optional<Coin> getCurrentEnteredCoin(String name) {
//        return Optional.of(currentEnteredCoins.get(name));
//    }

//    public void setCurrentMoney(Coin coin, int quantity) {
//        if (currentEnteredCoins.containsKey(coin.getName())) {
//            currentEnteredCoins.get(coin.getName()).setQuantity(quantity);
//        } else {
//            currentEnteredCoins.put(coin.getName(), new Coin(coin.getName(), coin.getWeight(), quantity));
//        }
//
//    }

//    public void addCurrentMoney(Coin coin) {
//        if (currentEnteredCoins.containsKey(coin.getName())) {
//            currentEnteredCoins.get(coin.getName()).setQuantity(currentEnteredCoins.get(coin.getName()).getQuantity() + 1);
//        } else {
//            currentEnteredCoins.put(coin.getName(), new Coin(coin.getName(), coin.getWeight(), 1));
//        }
//    }

    public void saveCurrentMoney() {
        for (Coin coin: coins) {
            coin.saveCurrentEnteredValue();
//            if (currentEnteredCoins.containsKey(coin.getName())){
//                Coin currentCoin = currentEnteredCoins.get(coin.getName());
//                coin.setQuantity(currentCoin.getQuantity() + coin.getQuantity());
//                currentCoin.setQuantity(0);
//            }
        }

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

    public Optional<Slot> getSlotByIndex(String index) {
        int slotIndex = Integer.parseInt(index);
        Slot slot = slots.get(slotIndex);
        if (slot != null) return Optional.of(slot);
        return Optional.empty();
    }

    public Optional<Slot> getSlotByIndex(int index) {
        Slot slot = slots.get(index);
        if (slot != null) return Optional.of(slot);
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
