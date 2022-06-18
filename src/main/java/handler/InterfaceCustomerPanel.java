package handler;

import objects.Coin;
import objects.Drink;

import java.util.List;

public interface InterfaceCustomerPanel {

    public List<String> getSupportedCoins();

    public List<Drink> getDrinks();

    public Coin getCoinByName(String name);
}
