package controller;


import objects.Coin;
import objects.Drink;
import objects.Machine;

import java.util.*;

public class DataController {

    private Machine machine;
    private Map<String, Coin> coinMap;
    private Map<String, Drink> drinkMap;

    public DataController() {
        this.coinMap = new HashMap<>();
        this.drinkMap = new HashMap<>();
        coinMap.put("test", new Coin("c", 10, 1));
    }

    /**
     * Check if the input a correct password.
     * @return boolean representing whether the password is correct
     */
    public boolean isValidPassword(String input){
        return machine.getPassword().equals(input);
//        return password == input;
//        return true;
    }

    /**
     * check if the drink name exist in the drinks,
     * @param name drink name
     * @return Option<Drink> means either the Drink was found or it is empty.
     */
    public Optional<Drink> getDrinkByName(String name){
        Drink drink = drinkMap.get(name);
        if (drink == null) return Optional.empty();

        return Optional.of(drink);
    }

    public Set<Drink> getDrinks() {
        return new HashSet<Drink>(drinkMap.values());
//        return drinks;
    }

    public void setDrink(String name, int price, int quantity) {
        if (price < 0 || quantity < 0) return;
        if (drinkMap.containsKey(name)) {
            Drink drink = drinkMap.get(name);
            drink.setPrice(price);
//            drink.setQuantity(quantity);
        } else {
//            this.drinkMap.put(name, new Drink(name, price, quantity));
        }
    }

    public void deleteDrink(String name) {
        drinkMap.remove(name);
    }

    /**
     * check if the Coin name exist in the drinks,
     * @param name coin name
     * @return Option<Coin> means either the Coin was found or it is empty.
     */
    public Optional<Coin> getCoinByName(String name){
        Coin coin = coinMap.get(name);
        if (coin == null) return Optional.empty();

        return Optional.of(coin);
    }

    /**
     * calculates the total cash in the machine
     * @return the total cash in the machine
     */
    public int getTotalCash(){
        int totalCash = 0;
        for(Coin coin: coinMap.values()){
            totalCash += coin.getTotalValue();
        }
        return totalCash;
    }

    public Set<Coin> getCoins() {
        return new HashSet<Coin>(coinMap.values());
    }

    public void setCoin(String type, int weight, int quantity) {
        if (weight <= 0 || quantity < 0) return;

        String name = weight + " " + type;
        if (coinMap.containsKey(name)) {
            Coin coin = coinMap.get(name);
            coin.setQuantity(quantity);
        } else {
            this.coinMap.put(name, new Coin(type, weight, quantity));
        }
    }

    public void deleteCoin(String name) {
        coinMap.remove(name);
    }

    public boolean getDoorStatus() {
        return false;
//        return this.machine.getDoorStatus();
    }

//    public void setDoorStatus(boolean status) {
//        this.machine.setDoorStatus(status);
//    }
}
